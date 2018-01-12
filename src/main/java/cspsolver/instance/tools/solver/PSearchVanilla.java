package cspsolver.instance.tools.solver;

import java.util.ArrayList;

import cspsolver.instance.components.PDomain;
import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;

import static cspsolver.instance.tools.solver.PSearchBase.remove;

public class PSearchVanilla implements BTSearch {

	private ArrayList<PVariable> currentPath;
	// Search related information
	private boolean consistent;
	private PInstance problem;
	private int[] assignments;
	private int Solutions;
	private ArrayList<PDomain> current_domains;

	public int bcssp(String status, PState state) {
		String stat;
		this.setConsistent(true);
		stat = status;
		int i = 1;
		int n = this.currentPath.size() - 1;
		String printOptionName = state.getPrintsolutions();
		String printOptionValue = state.getFindsolutions();

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

				if (printOptionName.equals("p")) {
					System.out.println();

					for (String var : problem.getOrderedVariableNames()) {
						for (int k = 1; k < this.currentPath.size(); k++) {
							if (var.equals(this.currentPath.get(k).getName()))
								System.out.print(
										" " + this.currentPath.get(k).getCurrent_domain().getValues()[0] + " ");
						}
					}
				}

				this.currentPath.get(i).getCurrent_domain()
						.remove(this.currentPath.get(i).getCurrent_domain().getValues()[0]);

				if (printOptionValue.equals("1")) {
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

		state.setBacktracks(state.getBacktracks() + 1);
		int h = i - 1;

		this.currentPath.get(i).getCurrent_domain().setValues(this.current_domains.get(i).getValues());
		remove(this.currentPath.get(h).getCurrent_domain(), this.assignments[h]);
		if (this.currentPath.get(h).getCurrent_domain() != null) {
			this.setConsistent(true);
		}

		return h;
	}

	public int bt_label(int i, boolean consistent, PState state) {
		this.setConsistent(false);
		int k = 0;
		while ((!this.isConsistent()) && (k < (this.currentPath.get(i).getCurrent_domain().getValues().length))) {
			state.setNodesVisited(state.getNodesVisited() + 1);
			this.setConsistent(true);
			this.assignments[i] = this.currentPath.get(i).getCurrent_domain().getValues()[k];

			int h = 1;
			while (this.isConsistent() && (h <= (i - 1))) {

				this.setConsistent(PSearchBase.check(i, h, state, assignments, currentPath));
				if (!this.isConsistent()) {
					remove(this.currentPath.get(i).getCurrent_domain(),
									this.currentPath.get(i).getCurrent_domain().getValues()[k]);
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

	@Override
	public int getSolutions() {
		return this.Solutions;
	}

	public ArrayList<PVariable> getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(ArrayList<PVariable> currentPath) {
		this.currentPath = currentPath;
	}

	public PInstance getProblem() {
		return problem;
	}

	public void setProblem(PInstance problem) {
		this.problem = problem;
	}

	public int[] getAssignments() {
		return assignments;
	}

	public void setAssignments(int[] assignments) {
		this.assignments = assignments;
	}

	public ArrayList<PDomain> getCurrent_domains() {
		return current_domains;
	}

	public void setCurrent_domains(ArrayList<PDomain> current_domains) {
		this.current_domains = current_domains;
	}

	public void setSolutions(int solutions) {
		Solutions = solutions;
	}

	public boolean isConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

}