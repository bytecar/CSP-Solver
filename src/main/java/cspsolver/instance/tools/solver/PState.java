package cspsolver.instance.tools.solver;

public class PState {

	private String search;
	private String variableOrderingHeuristics;
	private String variableStaticDynamic;
	private String cspname;
	private String printOptionName;
	private String printOptionValue;

	// Calculated
	private double cpu_time;
	private int constraintChecks;
	private int nodesVisited;
	private int backtracks;

	public PState(String search, String variableOrderingHeuristic, String variableStaticDynamic, String cspname,
			String solutions, String printsolutions) {
		this.search = search;
		this.variableOrderingHeuristics = variableOrderingHeuristic;
		this.cspname = cspname;
		this.variableStaticDynamic = variableStaticDynamic;
		this.setFindsolutions(solutions);
		this.setPrintsolutions(printsolutions);

	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public String getVariableOrderingHeuristics() {
		return variableOrderingHeuristics;
	}
	public void setVariableOrderingHeuristics(String variableOrderingHeuristics) {
		this.variableOrderingHeuristics = variableOrderingHeuristics;
	}
	public String getCspname() {
		return cspname;
	}
	public void setCspname(String cspname) {
		this.cspname = cspname;
	}
	public double getCpu_time() {
		return cpu_time;
	}
	public void setCpu_time(double cpu_time) {
		this.cpu_time = cpu_time;
	}
	public int getConstraintChecks() {
		return constraintChecks;
	}
	public void setConstraintChecks(int constraintChecks) {
		this.constraintChecks = constraintChecks;
	}
	public int getNodesVisited() {
		return nodesVisited;
	}
	public void setNodesVisited(int nodesVisited) {
		this.nodesVisited = nodesVisited;
	}
	public int getBacktracks() {
		return backtracks;
	}
	public void setBacktracks(int backtracks) {
		this.backtracks = backtracks;
	}

	public String getPrintsolutions() {
		return printOptionValue;
	}
	public void setPrintsolutions(String printsolutions) {
		this.printOptionValue = printsolutions;
	}

	public String getFindsolutions() {
		return printOptionName;
	}

	public void setFindsolutions(String findsolutions) {
		this.printOptionName = findsolutions;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nCSP(-f): " + cspname + "\nSearch(-a): " + search);

		sb.append("\nConstraint-Checks(CC): " + constraintChecks + "\nNodes-Visited(nv): " + nodesVisited
				+ "\nBacktracks(bt): " + backtracks + "\nVariable Ordering Dynamicity: " + variableStaticDynamic);

		sb.append("\nTime: " + cpu_time * 1000 + " ms");
		return sb.toString();
	}

	public String getVariableStaticDynamic() {
		return variableStaticDynamic;
	}

	public void setVariableStaticDynamic(String variableStaticDynamic) {
		this.variableStaticDynamic = variableStaticDynamic;
	}
}
