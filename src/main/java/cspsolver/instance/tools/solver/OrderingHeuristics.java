package cspsolver.instance.tools.solver;

import java.util.ArrayList;
import java.util.Collections;

import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;

public class OrderingHeuristics {
	PInstance csp;
	String variableOrderingHeuristic;

	public OrderingHeuristics(String variableOrderingHeuristic, PInstance cspInstance) {
		this.csp = cspInstance;
		this.variableOrderingHeuristic = variableOrderingHeuristic;

	}

	public ArrayList<String> runHeuristics() {
		if (variableOrderingHeuristic.equals("LX")) {
			return lexiCompLX();
		} else if (variableOrderingHeuristic.equals("LD")) {
		} else if (variableOrderingHeuristic.equals("W")) {
		} else if (variableOrderingHeuristic.equals("DEG")) {
		} else if (variableOrderingHeuristic.equals("DD")) {
		}
		return null;
	}

	public ArrayList<String> lexiCompLX() {
		ArrayList<String> varNames = new ArrayList<String>();
		ArrayList<String> orderedVarNames = new ArrayList<String>();
		for (PVariable var : csp.getVariables()) {
			varNames.add(var.getName());
			orderedVarNames.add(var.getName());
		}
		Collections.sort(varNames, new LexicographicComparator());
		ArrayList<PVariable> orderedVariables = new ArrayList<PVariable>();
		for (String var : varNames) {
			orderedVariables.add(csp.getMapOfVariables().get(var));
		}
		return orderedVarNames;
	}

	public ArrayList<String> minDomainLD()	{
		ArrayList<String> varNames = new ArrayList<String>();
		ArrayList<String> orderedVarNames = new ArrayList<String>();
		for (PVariable var : csp.getVariables()) {
			varNames.add(var.getName());
			orderedVarNames.add(var.getName());
		}
		return orderedVarNames;
	}
}
