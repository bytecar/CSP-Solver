package cspsolver.instance.tools.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;
import cspsolver.instance.tools.InstanceParser;

public abstract class PSearch {
	
	public PVariable[] current_path;
	public PInstance cspsolve;
	public boolean consistent;
	public int[] assignment;
	public int Solutions;
	public int[][] current_domains;
	Stack<Integer>[] future_fc;
	Stack<Integer>[] past_fc;
	Stack<Stack<Integer>>[] reductions;
	Stack<Integer> reduction;
	public HashMap<Integer, ArrayList<Integer>> conf_set;
	public InstanceParser parserRef;
		

	public boolean isConsistent() {
		return consistent;
	}

	public void unaryC(PState state, int i) {

		for (int j = 0; j < this.current_domains[i].length; j++) {

			this.assignment[0] = this.current_domains[i][j];

			//this.setConsistent(this.Ucheck(i, state));

			if (!(this.Ucheck(i, state))) {
				this.current_domains[i] = this.remove(this.current_domains[i], this.current_domains[i][j]);
				j--;
			}
		}
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	public PVariable[] getCurrent_path() {
		return current_path;
	}

	public PInstance getCspsolve() {
		return cspsolve;
	}

	public void setCspsolve(PInstance cspsolve) {
		this.cspsolve = cspsolve;
	}

	public int getcurrentpathlength() {
		return current_path.length;
	}

	public int bcssp(String status, PState state, String print, String solns) {

		String stat;
		this.setConsistent(true);
		stat = status;
		int i = 1;
		int n = this.getcurrentpathlength() - 1;

		while (stat.equals("unknown")) {

			if (this.isConsistent()) {
				//dynamic ordering//current_path[i] = csp.vars[i];
				i = bt_label(i, this.isConsistent(), state);
			} else {
				i = bt_unlabel(i, this.isConsistent(), state);
			}

			if (i > n) {
				status = "solution";
				//this.current_path[i].current_domain=null;
				i--;
				Solutions++;
				//return status;

				if (print.equals("p")) {
					System.out.println();
				
					for(int l=0;l<cspsolve.OVarName.length;l++)	{						
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
					status = "impossible";
					return Solutions;
				}
			}
		}

		return Solutions;
	}

	public int bt_unlabel(int i, boolean consistent, PState state) {
		//System.out.println("Backtrack");
		(state.bt)++;
		int h = i - 1;
		this.current_path[i].current_domain = new int[this.current_domains[i].length];
		//System.arraycopy(this.current_path[i].getDomain().getValues(), 0, this.current_path[i].current_domain, 0, this.current_path[i].getDomain().getValues().length);
		System.arraycopy(this.current_domains[i], 0, this.current_path[i].current_domain, 0, this.current_domains[i].length);

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

		while ((!this.isConsistent()) && (k < (this.current_path[i].currentdomlength()))) {
			state.nv++;
			this.setConsistent(true);
			this.assignment[i] = this.current_path[i].current_domain[k];
			//System.out.println("Level = " + i + "  Value = " + this.current_path[i].current_domain[k]);

			int h = 1;
			while (this.isConsistent() && (h <= (i - 1))) {

				//for(int p=0;p<this.current_path[h].getDomain().getValues().length;p++)    {
				//this.current_path[h].assignment=this.current_path[h].domain.values[k];

				// this.setConsistent(Ucheck(i,state));

				//this.setConsistent(true);

				this.setConsistent(check(i, h, state));
				if (!this.isConsistent()) {
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



		if (this.isConsistent()) {
			return (i + 1);
		} else {
			return i;
		}
	}

	public boolean Ucheck(int i, PState state) {
		boolean status = false;

		for (int c1 = 0; c1 < this.current_path[i].numConstraints; c1++) {
			if (this.current_path[i].constraint[c1].getArity() == 1) {

				(state.CC)++;

				if (this.current_path[i].constraint[c1].type.equals("supports")) {
					//return (Uextension(this.current_path[i].constraint[c1].relation.getTuples(), this.assignment[0]));
					return (this.current_path[i].constraint[c1].computeCostOf(this.assignment) == 1 ? false : true);
				} else if (this.current_path[i].constraint[c1].type.equals("conflicts")) {
					return ((this.current_path[i].constraint[c1].computeCostOf(this.assignment) == 1 ? false : true));
				} else {
					//return (Uintensioncheckij(0, (PIntensionConstraint) this.current_path[i].constraint[c1], state));
					return (this.current_path[i].constraint[c1].computeCostOf(this.assignment) == 1 ? false : true);
				}
			}
		}
		return (true);
	}

	public int[] remove(int[] symbols, int c) {
		if (symbols == null) {
			return null;
		} else {

			int[] copy = new int[symbols.length - 1];
			for (int i = 0; i < symbols.length; i++) {
				if (symbols[i] == c) {

					System.arraycopy(symbols, 0, copy, 0, i);
					System.arraycopy(symbols, i + 1, copy, i, symbols.length - i - 1);
					break;
				}
			}

			return (copy);
		}
	}

	public boolean check(int i, int j, PState state) {

		boolean status = false;
		int[] temptuples = new int[2];

		temptuples[0] = assignment[j];
		temptuples[1] = assignment[i];

		for (int c1 = 0; c1 < this.current_path[i].numConstraints; c1++) {
			for (int c2 = 0; c2 < this.current_path[j].numConstraints; c2++) {
				if (this.current_path[i].constraint[c1].getName().equals(this.current_path[j].constraint[c2].getName())) {

					(state.CC)++;

					if (this.current_path[i].constraint[c1].type.equals("supports")) {
						//return (exstension(this.current_path[i].constraint[c1].relation.getTuples(), this.assignment[i], this.assignment[j]));
						return (this.current_path[i].constraint[c1].computeCostOf(temptuples) == 1 ? false : true);

					} else if (this.current_path[i].constraint[c1].type.equals("conflicts")) {
						//return (!extension(this.current_path[i].constraint[c1].relation.getTuples(), this.assignment[i], this.assignment[j]));
						return ((this.current_path[i].constraint[c1].computeCostOf(temptuples) == 1 ? false : true));

					} else {
						//return (intensioncheckij(i, j, (PIntensionConstraint) this.current_path[i].constraint[c1], state));
						return (this.current_path[i].constraint[c1].computeCostOf(temptuples) == 1 ? false : true);

					}
				}
			}
		}
		return true;
	}
	
	public ArrayList<Integer> union_al(ArrayList<Integer> list1, ArrayList<Integer> list2) {


		ArrayList<Integer> union = new ArrayList<Integer>();

		if (list1 == null && list2 == null) {

			return union;
		} else if ((list1 == null) && list2 != null) {

			for (Integer j : list2) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}
			return union;
		} else if ((list2 == null) && list1 != null) {

			for (Integer j : list1) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}
			return union;
		} else {

			for (Integer j : list1) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}

			for (Integer i : list2) {
				if (union.contains(i) == false) {
					union.add(i);
				}
			}
			return union;
		}
	}
	
	public ArrayList<Integer> union_al(ArrayList<Integer> list1, Stack<Integer> list2) {

		ArrayList<Integer> union = new ArrayList<Integer>();

		if (list1 == null && list2 == null) {

			return union;
		} else if ((list1 == null) && list2 != null) {

			for (Integer j : list2) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}
			return union;
		} else if ((list2 == null) && list1 != null) {

			for (Integer j : list1) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}
			return union;
		} else {

			for (Integer j : list1) {
				if (union.contains(j) == false) {
					union.add(j);
				}
			}

			for (Integer i : list2) {
				if (union.contains(i) == false) {
					union.add(i);
				}
			}
			return union;
		}
	}
	
	int[] listtoInt(ArrayList<Integer> list) {

		int[] arr = new int[list.size()];
		int j = 0;
		for (Integer i : list) {
			arr[j] = i.intValue();
			j++;

			if (j == list.size()) {
				break;
			}
		}
		return arr;
	}

	ArrayList<Integer> intoToList(int[] list) {

		ArrayList<Integer> arr = new ArrayList<Integer>(list.length);

		for (int i = 0; i < list.length; i++) {
			arr.add(list[i]);
		}
		return arr;
	}

	int[] set_diff(int[] list1, Stack<Integer> list2) {

		ArrayList<Integer> setdiff = new ArrayList<Integer>();

		if (list1 == null && list2 == null) {

			return null;
		} else if ((list1 == null) && list2 != null) {

			return null;
		} else if ((list2 == null) && list1 != null) {

			for (Integer j : list1) {
				if (setdiff.contains(j) == false) {
					setdiff.add(j);
				}
			}
			return listtoInt(setdiff);
		} else {

			for (Integer j : list1) {
				if (setdiff.contains(j) == false) {
					setdiff.add(j);
				}
			}

			for (Integer i : list2) {
				if (setdiff.contains(i) == true) {
					setdiff.remove(i);
				}
			}
			return listtoInt(setdiff);
		}
	}
	
	boolean check_forward(int i, int j, PState state) {

		reduction = new Stack<Integer>();

		for (int k = 0; k < this.current_path[j].currentdomlength(); k++) {
			this.assignment[j] = this.current_path[j].current_domain[k];

			if (!check(i, j, state)) {
				reduction.push(this.current_path[j].current_domain[k]);
			}
		}

		if (!reduction.isEmpty()) {

			this.current_path[j].current_domain = set_diff(this.current_path[j].current_domain, reduction);
			this.reductions[j].push(reduction);
			future_fc[i].push(j);
			past_fc[j].push(i);
		}


		if (this.current_path[j].current_domain != null) {
			return (true);
		} else {
			return (false);
		}
	}
	
	void undo_reductions(int i) {

		if(future_fc[i] != null)	{
		for (Integer j : future_fc[i]) {
			reduction = reductions[j].pop();
			current_path[j].current_domain = listtoInt(union_al(intoToList(current_path[j].current_domain), reduction));
			past_fc[j].pop();
		}

		future_fc[i].clear();
		}
	}
	
	void updated_current_domain(int i) {
		current_path[i].current_domain= new int[current_domains[i].length];
		System.arraycopy(current_domains[i], 0, current_path[i].current_domain, 0, current_domains[i].length);
		//reduction = reductions[i].peek();
		for(Stack<Integer> idx:reductions[i])	{
			
		current_path[i].current_domain = set_diff(current_path[i].current_domain, idx);
		}
	}
}