package cspsolver.instance.tools.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;

public class PSearchFCCBJ extends PSearch	{
	
	int max_list(Stack<Integer> list,int i)	{
		
		if(list != null && list.size() !=0)
			return(i-1);
		else
			return (i-1);
	}
	
	int max_list2(int i)	{
		
		if(conf_set.get(i) != null && conf_set.get(i).size() !=0)
			return(Collections.max(conf_set.get(i)));
		else
			return (i-1);
	}

	int fccbj_label(int i, boolean status, PState state) {


		this.setConsistent(false);


		int k = 0;

		while ((!this.isConsistent()) && (k < (this.current_path[i].currentdomlength()))) {
			state.nv++;

			this.setConsistent(true);
			this.assignment[i] = this.current_path[i].current_domain[k];
			//System.out.println("Level = " + i + "  Value = " + this.current_path[i].current_domain[k]);

			int j = i + 1;
			while (this.isConsistent() && (j <= (current_path.length - 1))) {


				this.setConsistent(check_forward(i, j, state));

				if (!this.isConsistent()) {
					this.current_path[i].current_domain = remove(this.current_path[i].current_domain, this.current_path[i].assignment[i]);
					k--;
					undo_reductions(i);
					conf_set.put(i, this.union_al(conf_set.get(i), past_fc[j-1]));
				}
				j++;
			}

			k++;

		}

		if (this.isConsistent()) {
			return (i + 1);
		} else {
			return i;
		}

	}
	
	public int fccbj_unlabel(int i, boolean consistent, PState state) {
		//System.out.println("Backtrack");
		(state.bt)++;
		
		int h,temp;
		
		temp=max_list(past_fc[i],i);
		h=((h=max_list2(i))>temp?h:temp);
		
		conf_set.put(h,intoToList(remove(listtoInt(union_al2(conf_set.get(h),union_al(conf_set.get(i),past_fc[i]))),h)));
		
		for(int j=i;j>=h+1;j--)	{
			ArrayList<Integer> temp1= new ArrayList<Integer>();
			temp1.add(0);
			
			conf_set.put(j,temp1);
			undo_reductions(j);
			updated_current_domain(j);		
		}
		undo_reductions(h);		
		this.current_path[h].current_domain = remove(this.current_path[h].current_domain, this.assignment[h]);

		if (this.current_path[h].getCurrent_domain() != null) 
			this.setConsistent(true);
		else	
			this.setConsistent(false);

		return h;
	}

	public ArrayList<Integer> union_al2(ArrayList<Integer> list1, ArrayList<Integer> list2) {

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
	
	public int bcssp(String status, PState state, String print, String solns) {

		String stat;
		this.setConsistent(true);
		stat = status;
		int i = 1;
		int n = this.getcurrentpathlength() - 1;

		while (stat.equals("unknown")) {

			if (this.isConsistent()) {
				//dynamic ordering//current_path[i] = csp.vars[i];
				i = fccbj_label(i, this.isConsistent(), state);
			} else {
				i = fccbj_unlabel(i, this.isConsistent(), state);
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
	
}