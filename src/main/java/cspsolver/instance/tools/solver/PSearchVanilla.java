package cspsolver.instance.tools.solver;

public class PSearchVanilla extends PSearch {

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

}