package cspsolver.instance.tools.solver;

import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;
import cspsolver.instance.tools.InstanceParser;

import java.util.*;

public class OrderingHeuristics {

	private Map<String, String> mapOfDomVar;
	private Map<String, String> mapOfDDRVar;
	private Map<String, String> mapOfConsVar;
	PInstance csp;
	InstanceParser parser;
	
	OrderingHeuristics(String variableOrderingHeuristics,String valueOrderingHeuristics,PInstance cspInstance)	{
		this.csp = cspInstance;
		
		if(variableOrderingHeuristics.equals("LX"))	{
			lexicomp();
		}
		else if(variableOrderingHeuristics.equals("LD"))	{
			mindomain();
		}
		else if(variableOrderingHeuristics.equals("W"))	{
			minWidth();
		}
		else if(variableOrderingHeuristics.equals("DEG"))	{
			maxdegree();
		}
		else if (variableOrderingHeuristics.equals("DD"))	{
			minDDR();
		}
	}
		
	public void lexicomp() {

		String[] varlist = new String[csp.vars.length];
		csp.OVarName = new String[csp.vars.length];
		lexicomparator lexi = new lexicomparator(parser);

		for (int i = 0; i < csp.vars.length; i++) {
			varlist[i] = csp.vars[i].getName();
			csp.OVarName[i] = csp.vars[i].getName();
		}
		
		Arrays.sort(varlist, lexi);

		for (int i = 0; i < csp.vars.length; i++) {
			//System.out.println("varlist["+i+"]="+varlist[i]);
			csp.vars[i] = parser.mapOfVariables.get(varlist[i]);
			
		}
	}

	public void mindomain() {

		this.lexicomp();

		int[] varlist = new int[csp.vars.length];

		mapOfDomVar = new HashMap<String, String>();
		HashMap<String, String> tmp = new HashMap<String, String>();

		for (int i = 0; i < csp.vars.length; i++) {
			varlist[i] = csp.vars[i].getDomain().getValues().length;
			mapOfDomVar.put(csp.vars[i].getName(), "" + varlist[i]);
		}
		//Arrays.sort(varlist);
		
		
                //ascending order
		tmp = sortHashMapByValues((HashMap<String,String>) mapOfDomVar);

		/*  for(int i=0;i<csp.vars.length;i++) {
		csp.vars[i]=mapOfVariables.get(mapOfDomVar.get(csp.vars[i].getName()));
		System.out.println("varlist["+i+"]="+varlist[i]+" "+csp.vars[i].getName());
		}*/

		Set<String> ref = tmp.keySet();
		Iterator<String> it = ref.iterator();

		int i = 0;
		while (it.hasNext()) {
			String file = (String) it.next();
			System.out.println("var: " +file+"  Dom: "+tmp.get(file));
			csp.vars[i] = parser.mapOfVariables.get(file.trim());
			i++;
		}

	}
        
	public void minDDR() {


		this.lexicomp();

		float[] varlist = new float[csp.vars.length];
		mapOfDDRVar = new HashMap<String, String>();
		HashMap<String, String> tmp = new HashMap<String, String>();

		for (int i = 0; i < csp.vars.length; i++) {

			if ((csp.vars[i].numConstraints) != 0) {
				varlist[i] = (csp.vars[i].getDomain().getValues().length) / (csp.vars[i].numConstraints);
			} else {
				varlist[i] = (csp.vars[i].getDomain().getValues().length);
			}
                        
			mapOfDDRVar.put(csp.vars[i].getName(), "" + varlist[i]);
		}
		//Arrays.sort(varlist);

		//tmp = sortHashMapByValues((HashMap) mapOfDDRVar);

                tmp=(HashMap<String, String>) mapOfDDRVar;
                
		/*
		for(int i=0;i<parser.csp.vars.length;i++) {
		parser.csp.vars[i]=parser.mapOfVariables.get(mapOfDomVar.get(parser.csp.vars[i].getName()));
		System.out.println("varlist["+i+"]="+varlist[i]+" "+parser.csp.vars[i].getName());
		}*/
		 
		Set<String> ref = tmp.keySet();
		Iterator<String> it = ref.iterator();

		int i = 0;
		while (it.hasNext()) {
			String file = it.next();
			System.out.println("varlist[]=" + file);
			csp.vars[i] = parser.mapOfVariables.get(file.trim());
			i++;
		}

	}

	public HashMap<String,String> sortHashMapByValues(HashMap<String,String> passedMap) {
		
		List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
		List<String> mapValues = new ArrayList<String>(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		HashMap<String,String> sortedMap = new LinkedHashMap<String,String>();

		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String) key, (String) val);
					break;
				}

			}

		}
		return sortedMap;
	}

	public LinkedHashMap sortHashMapByValuesR(HashMap passedMap) {
		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());
		Collections.sort(mapValues, Collections.reverseOrder());
		Collections.sort(mapKeys, Collections.reverseOrder());

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String) key, (String) val);
					break;
				}

			}

		}
		return sortedMap;
	}
	
	public void maxdegree() {

		this.lexicomp();

		int[] varlist = new int[csp.vars.length];

		mapOfConsVar = new HashMap<String, String>();
		HashMap<String, String> tmp = new HashMap<String, String>();

		for (int i = 0; i < csp.vars.length; i++) {
			varlist[i] = csp.vars[i].numConstraints;
			mapOfConsVar.put(csp.vars[i].getName(), "" + varlist[i]);
		}
		//Arrays.sort(varlist);

		//tmp = sortHashMapByValues((HashMap) mapOfConsVar);

		/*  for(int i=0;i<csp.vars.length;i++) {
		csp.vars[i]=mapOfVariables.get(mapOfDomVar.get(csp.vars[i].getName()));
		System.out.println("varlist["+i+"]="+varlist[i]+" "+csp.vars[i].getName());
		}*/

		Set<String> ref = tmp.keySet();
		Iterator<String> it = ref.iterator();

		int i = 0;
		while (it.hasNext()) {
			i++;
			String file = (String) it.next();
			//System.out.println("varlist[]="+file);
			csp.vars[(csp.vars.length) - i] = parser.mapOfVariables.get(file.trim());				
		}



		/* for (i = 0; i < csp.vars.length; i++) {
		System.out.println(csp.vars[i].getName());
		}*/

	}

	public void minWidth() {

		this.lexicomp();
		PVariable[] temp = new PVariable[csp.vars.length];
		ArrayList<PVariable> not_connected = new ArrayList<PVariable>();
		ArrayList<PVariable> widthSorted = new ArrayList<PVariable>();

		for (int i = 0; i < csp.vars.length; i++) {
			//System.out.println("varlist["+i+"]="+varlist[i]);
			temp[i] = csp.vars[csp.vars.length - i - 1];
		}

		System.arraycopy(temp, 0, csp.vars, 0, csp.vars.length);

		for (int i = 0; i < csp.vars.length; i++) {
			//System.out.println("varlist["+i+"]="+varlist[i]);

			if (csp.vars[i].neighbors.length == 0) {
				csp.vars = remove(csp.vars, csp.vars[i], not_connected);
			}

			int k = 0;
			while (csp.vars.length != 0) {
				k++;
				if (csp.vars[i].neighbors.length <= k) {
					csp.vars = remove(csp.vars, csp.vars[i], widthSorted);
				}
			}
		}

		Collections.reverse(widthSorted);

		if (!not_connected.isEmpty()) {
			widthSorted = union_al(widthSorted, not_connected);
		}

		csp.vars = new PVariable[temp.length];
		widthSorted.toArray(csp.vars);
		
		for (int i = 0; i < csp.vars.length; i++) {
			//System.out.println("varlist["+i+"]="+csp.vars[i].getName());
			
		}
		
	}

	public PVariable[] remove(PVariable[] symbols, PVariable c, ArrayList<PVariable> removed) {
		if (symbols == null) {
			return null;
		} else {

			PVariable[] copy = new PVariable[symbols.length - 1];
			for (int i = 0; i < symbols.length; i++) {

				if (symbols[i].getName().equals(c.getName())) {
					removed.add(symbols[i]);
					System.arraycopy(symbols, 0, copy, 0, i);
					System.arraycopy(symbols, i + 1, copy, i, symbols.length - i - 1);
					break;
				}
			}


			return (copy);
		}
	}

	public ArrayList<PVariable> union_al(ArrayList<PVariable> list1, ArrayList<PVariable> list2) {

		ArrayList<PVariable> union = new ArrayList<PVariable>();

		if (list1 == null && list2 == null) {

			return union;
		} else if ((list1 == null) && list2 != null) {

			for (PVariable j : list2) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}
			return union;
		} else if ((list2 == null) && list1 != null) {

			for (PVariable j : list1) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}
			return union;
		} else {

			for (PVariable j : list1) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}

			for (PVariable i : list2) {
				if (union.contains(i) == false) {
					union.add(i);
				}
			}
			return union;
		}


	}
}
