package cspsolver.instance.tools.solver;

public class PSearchFC extends PSearchBase {
	/*

	int fc_label(int i, boolean status, PState state) {


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
					this.current_path[i].current_domain = remove(this.current_path[i].current_domain, this.current_path[i].current_domain[k]);
					k--;
					undo_reductions(i);
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
	
	public int fc_unlabel(int i, boolean consistent, PState state) {
		//System.out.println("Backtrack");
		(state.bt)++;
		int h = i - 1;
		undo_reductions(h);
		updated_current_domain(i);
				
		this.current_path[h].current_domain = remove(this.current_path[h].current_domain, this.assignment[h]);

		if (this.current_path[h].getCurrent_domain() != null) 
			this.setConsistent(true);
		else	
			this.setConsistent(false);

		return h;
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
				i = fc_label(i, this.isConsistent(), state);
			} else {
				i = fc_unlabel(i, this.isConsistent(), state);
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
		
*/
}