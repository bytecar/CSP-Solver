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
	
	OrderingHeuristics(String variableOrderingHeuristics,String valueOrderingHeuristics,PInstance cspInstance, InstanceParser parser)	{
		this.csp = cspInstance;
		this.parser = parser;
		
		if(variableOrderingHeuristics.equals("LX"))	{
			lexicomp();
		}
		else if(variableOrderingHeuristics.equals("LD"))	{
		}
		else if(variableOrderingHeuristics.equals("W"))	{
		}
		else if(variableOrderingHeuristics.equals("DEG"))	{
		}
		else if (variableOrderingHeuristics.equals("DD"))	{
		}
	}
		
	public void lexicomp() {

		ArrayList<String> varNames = new ArrayList<String>();
		ArrayList<String> orderedVarNames = new ArrayList<String>();
		
		lexicomparator lexi = new lexicomparator(parser);

		for(PVariable var: csp.getVariables()){
			varNames.add(var.getName());
			orderedVarNames.add(var.getName());
		}

		Collections.sort(varNames, lexi);

		ArrayList<PVariable> orderedVariables = new ArrayList<PVariable>();
		for (String var: varNames) {
			orderedVariables.add(parser.getMapOfVariables().get(var));
			
		}
		
		csp.setOrderedVariableNames(orderedVarNames);
	}
 
}
