package cspsolver.instance.tools.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.junit.experimental.theories.internal.Assignments;

import cspsolver.instance.components.PConstraint;
import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;
import cspsolver.instance.tools.InstanceParser;

public abstract class PSearchToolkit {

	// State information
	private PState state;
	// Search related information
	private boolean consistent;
	private ArrayList<PVariable> currentPath;
	protected PInstance problem;
	protected int[] assignments;
	protected int Solutions;
	protected ArrayList<int[]> current_domains;
	private ArrayList<Stack<Integer>> futureForwardChecks;
	private ArrayList<Stack<Integer>> pastForwardChecks;
	private ArrayList<Stack<Stack<Integer>>> reductions;
	private Stack<Integer> reduction;
	private HashMap<Integer, ArrayList<Integer>> conf_set;
	private InstanceParser parserRef;

	
	public PState getState() {
		return state;
	}

	public void setState(PState state) {
		this.state = state;
	}

	public boolean isConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
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

	public int[] getAssignment() {
		return assignments;
	}

	public void setAssignment(int[] assignment) {
		this.assignments = assignment;
	}

	public int getSolutions() {
		return Solutions;
	}

	public void setSolutions(int solutions) {
		Solutions = solutions;
	}

	public ArrayList<int[]> getCurrent_domains() {
		return current_domains;
	}

	public void setCurrent_domains(ArrayList<int[]> current_domains) {
		this.current_domains = current_domains;
	}

	public ArrayList<Stack<Integer>> getFutureForwardChecks() {
		return futureForwardChecks;
	}

	public void setFutureForwardChecks(ArrayList<Stack<Integer>> futureForwardChecks) {
		this.futureForwardChecks = futureForwardChecks;
	}

	public ArrayList<Stack<Integer>> getPastForwardChecks() {
		return pastForwardChecks;
	}

	public void setPastForwardChecks(ArrayList<Stack<Integer>> pastForwardChecks) {
		this.pastForwardChecks = pastForwardChecks;
	}

	public ArrayList<Stack<Stack<Integer>>> getReductions() {
		return reductions;
	}

	public void setReductions(ArrayList<Stack<Stack<Integer>>> reductions) {
		this.reductions = reductions;
	}

	public Stack<Integer> getReduction() {
		return reduction;
	}

	public void setReduction(Stack<Integer> reduction) {
		this.reduction = reduction;
	}

	public HashMap<Integer, ArrayList<Integer>> getConf_set() {
		return conf_set;
	}

	public void setConf_set(HashMap<Integer, ArrayList<Integer>> conf_set) {
		this.conf_set = conf_set;
	}

	public InstanceParser getParserRef() {
		return parserRef;
	}

	public void setParserRef(InstanceParser parserRef) {
		this.parserRef = parserRef;
	}

	public static void unaryC(ArrayList<int[]> current_domains, PState state, int i, int[] assignments, ArrayList<PVariable> currentPath) {

		for (int j = 0; j < current_domains.get(i).length; j++) {

			assignments[0] = current_domains.get(i)[j];

			if (!(Ucheck(i, state, currentPath, assignments))) {
				current_domains.set(i, remove(current_domains.get(i), current_domains.get(i)[j]));
				j--;
			}
		}
	}

	public static int[] remove(int[] symbols, int c) {
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

	public static boolean Ucheck(int i, PState state, ArrayList<PVariable> currentPath, int[] assignments) {

		for (PConstraint cons: currentPath.get(i).getConstraints()) {
			if (cons.getArity() == 1) {

				state.setConstraintChecks(state.getConstraintChecks()+1);

				if (cons.getType().equals("supports")) {
					return (cons.computeCostOf(assignments) == 1 ? false : true);
				} else if (cons.getType().equals("conflicts")) {
					return ((cons.computeCostOf(assignments) == 1 ? false : true));
				} else {
					return (cons.computeCostOf(assignments) == 1 ? false : true);
				}
			}
		}
		return (true);
	}
	
	
	public static boolean check(int i, int j, PState state, int[] assignments, ArrayList<PVariable> currentPath) {

		boolean status = false;
		int[] temptuples = new int[2];

		temptuples[0] = assignments[j];
		temptuples[1] = assignments[i];

		for (PConstraint cons1: currentPath.get(i).getConstraints()) {
			for (PConstraint cons2: currentPath.get(j).getConstraints()) {
				
				if (cons1.getName().equals(cons2.getName())) {

					state.setConstraintChecks(state.getConstraintChecks()+1);

					if (cons1.getType().equals("supports")) {
						// return
						// (exstension(this.current_path[i].constraint[c1].relation.getTuples(),
						// this.assignment[i], this.assignment[j]));
						return (cons1.computeCostOf(temptuples) == 1 ? false : true);

					} else if (cons1.getType().equals("conflicts")) {
						// return
						// (!extension(this.current_path[i].constraint[c1].relation.getTuples(),
						// this.assignment[i], this.assignment[j]));
						return ((cons1.computeCostOf(temptuples) == 1 ? false : true));

					} else {
						// return (intensioncheckij(i, j, (PIntensionConstraint)
						// this.current_path[i].constraint[c1], state));
						return (cons1.computeCostOf(temptuples) == 1 ? false : true);

					}
				}
			}
		}
		return true;
	}

	public static ArrayList<Integer> union_al(ArrayList<Integer> list1, ArrayList<Integer> list2) {

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

	public static ArrayList<Integer> union_al(ArrayList<Integer> list1, Stack<Integer> list2) {

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

	public static int[] listtoInt(ArrayList<Integer> list) {

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

	public static ArrayList<Integer> intoToList(int[] list) {

		ArrayList<Integer> arr = new ArrayList<Integer>(list.length);

		for (int i = 0; i < list.length; i++) {
			arr.add(list[i]);
		}
		return arr;
	}

	public static int[] set_diff(int[] list1, Stack<Integer> list2) {

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


}