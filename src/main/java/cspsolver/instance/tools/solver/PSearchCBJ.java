package cspsolver.instance.tools.solver;

import java.util.ArrayList;
import java.util.Collections;

import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;


public class PSearchCBJ extends PSearchBase {
	
	private ArrayList<PVariable> currentPath;
	// State information
	private PState state;
	// Search related information
	private boolean consistent;
	private PInstance problem;
	private int[] assignments;
	private int Solutions;
	private ArrayList<int[]> current_domains;

	public boolean isConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	public int bcssp(String status, PState state, String print, String solns) {

		String stat;
		this.setConsistent(true);
		stat = status;
		int i = 1;
		int p = 0;
		ArrayList<Integer> ptmp = new ArrayList<Integer>();
		/*int n = this.getcurrentpathlength() - 1;

		
		while (stat.equals("unknown")) {

			if (this.isConsistent()) {
				//dynamic ordering//current_path[i] = csp.vars[i];
				i = bt_label(i, this.isConsistent(), state);
			} else {
				i = bt_unlabel(i, this.isConsistent(), state);
			}

			if (i > n) {
				//stat = "solution";
				//this.current_path[i].current_domain=null;

				i--;
				Solutions++;
				//return Solutions;
				//p = i - 1;				
				for (p = 1; p < i; p++) {
					ptmp.add(p);
				}
				//System.out.println(ptmp.size());
				getConf_set().put(i, ptmp);
				//System.out.println("Soultions"+Solutions+"\n i"+i);
				//System.out.println("Solution: ");

				if (print.equals("p")) {
					System.out.println();
				
					for(int l=0;l<cspsolve.OVarName.length;l++)	{	
						//System.out.println(cspsolve.OVarName[l]);
					for (int k = 1; k < this.current_path.length; k++) {						
						if(cspsolve.OVarName[l].equals(this.current_path[k].getName()))
						System.out.print("	" + this.current_path[k].current_domain[0] + " ");
					}
				}
				}	

				this.current_path[i].current_domain = remove(this.current_path[i].current_domain, this.current_path[i].current_domain[0]);

				if (solns.equals("1")) {
					return Solutions;
				}

				stat = "unknown";

			} else {
				if (i == 0) {
					stat = "impossible";
					return Solutions;
				}
			}
		}

		return Solutions;
	}

	public int bt_unlabel(int i, boolean consistent, PState state) {
		//System.out.println("Backtrack");
		(state.bt)++;
		int h;
		if (!getConf_set().get(i).isEmpty()) {
			h = Collections.max(getConf_set().get(i));
		} else {
			h = 0;
			return h;
		}
		
		ArrayList<Integer> temp;

		//System.out.println("i: "+i+"h"+h+"\n ");
		//System.out.println("h: conf_Set(h)"+!conf_set.get(h).isEmpty()+"i: conf_Set(i)"+!conf_set.get(i).isEmpty());

		getConf_set().put(h, union_al(getConf_set().get(h), getConf_set().get(i)));

		temp = getConf_set().get(h);
		temp.remove(temp.indexOf(h));
		getConf_set().put(h, temp);

		for (int j = h + 1; j <= i; j++) {
			temp = getConf_set().get(j);
			temp.clear();
			//temp.add(0);
			getConf_set().put(j, temp);

			this.current_path[j].current_domain = new int[this.current_domains[j].length];
			//System.arraycopy(this.current_path[j].getDomain().getValues(), 0, this.current_path[j].current_domain, 0, this.current_path[j].getDomain().getValues().length);
			System.arraycopy(this.current_domains[j], 0, this.current_path[j].current_domain, 0, this.current_domains[j].length);
		}

		//if (this.current_path[h].getCurrent_domain() != null)
		this.current_path[h].current_domain = remove(this.current_path[h].current_domain, this.assignment[h]);


		if (this.current_path[h].getCurrent_domain() != null) {
			this.setConsistent(true);
		}

		return h;
	}

	public int bt_label(int i, boolean consistent, PState state) {


		this.setConsistent(false);
		int k = 0;
		ArrayList<Integer> temp;

		while ((!this.isConsistent()) && (k < (this.current_path[i].currentdomlength()))) {
			state.nv++;
			this.setConsistent(true);
			this.assignment[i] = this.current_path[i].current_domain[k];
			//System.out.println("Level = " + i + "  Value = " + this.current_path[i].current_domain[k]);

			int h = 1;
			while (this.isConsistent() && (h <= (i - 1))) {

				//for(int p=0;p<this.current_path[h].getDomain().getValues().length;p++)    {
				//this.current_path[h].assignment=this.current_path[h].domain.values[k];
				this.setConsistent(check(i, h, state));

				if (!this.isConsistent()) {

					temp = getConf_set().get(i);

					if (!temp.contains(h)) {
						temp.add(h);
						getConf_set().put(i, temp);
					}

					this.current_path[i].current_domain = remove(this.current_path[i].current_domain, this.current_path[i].current_domain[k]);
					k--;
				}

				h++;
				//}

			}

			k++;

			//if(i>this.cspsolve.vars.length)
			//this.current_path[i].current_domain = remove(this.current_path[i].current_domain, this.current_path[i].current_domain[k]);


		}

*/

		if (this.isConsistent()) {
			return (i + 1);
		} else {
			return i;
		}
	}

}