package cspsolver.instance.tools.solver;

public class PSearchVanilla extends PSearch {

	public int bcssp(String status, PState state) {

		String stat;
		this.setConsistent(true);
		stat = status;
		int i = 1;
		int n = this.getCurrentPath().size() - 1;
		String print = state.getPrintsolutions();
		String solns = state.getFindsolutions();
		
		while (stat.equals("unknown")) {

			if (this.isConsistent()) {
				// dynamic ordering//current_path[i] = csp.vars[i];
				i = bt_label(i, this.isConsistent(), state);
			} else {
				i = bt_unlabel(i, this.isConsistent(), state);
			}

			if (i > n) {
				status = "solution";
				// this.current_path[i].current_domain=null;
				i--;
				Solutions++;
				// return status;

				if (print.equals("p")) {
					System.out.println();

					for (String var: problem.getOrderedVariableNames()) {
						for (int k = 1; k < this.getCurrentPath().size(); k++) {
							if (var.equals(this.getCurrentPath().get(k).getName()))
								System.out.print("	" + this.getCurrentPath().get(k).getCurrent_domain().getValues()[0] + " ");
						}
					}
				}
				
				this.getCurrentPath().get(i).getCurrent_domain().remove(this.getCurrentPath().get(i).getCurrent_domain().getValues()[0]);

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
		
		state.setBacktracks(state.getBacktracks()+1);
		int h = i - 1;
		
		this.getCurrentPath().get(i).getCurrent_domain().setValues(this.current_domains.get(i));

		this.getCurrentPath().get(h).getCurrent_domain().setValues(remove(this.getCurrentPath().get(h).getCurrent_domain().getValues(), this.assignments[h]));

		if (this.getCurrentPath().get(h).getCurrent_domain() != null) {
			this.setConsistent(true);
		}

		return h;
	}

	public int bt_label(int i, boolean consistent, PState state) {

		this.setConsistent(false);

		int k = 0;

		while ((!this.isConsistent()) && (k < (this.getCurrentPath().get(i).getCurrent_domain().getValues().length ))) {
			state.setNodesVisited(state.getNodesVisited()+1);
			this.setConsistent(true);
			this.assignments[i] = this.getCurrentPath().get(i).getCurrent_domain().getValues()[k];

			int h = 1;
			while (this.isConsistent() && (h <= (i - 1))) {


				this.setConsistent(check(i, h, state));
				if (!this.isConsistent()) {
					this.getCurrentPath().get(i).getCurrent_domain().setValues(remove(this.getCurrentPath().get(i).getCurrent_domain().getValues(), this.getCurrentPath().get(i).getCurrent_domain().getValues()[k]));
					k--;
				}

				h++;
			}

			k++;
		}

		if (this.isConsistent()) {
			return (i + 1);
		} else {
			return i;
		}
	}

}