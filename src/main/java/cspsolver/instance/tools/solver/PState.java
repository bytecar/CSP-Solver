package cspsolver.instance.tools.solver;

public class PState {

	private String search;
	private String valueOrderingHeuristic;
	private String variableOrderingHeuristics;
	private String cspname;
	private String findsolutions;
	private String dynamicity;
	private String printsolutions;
	
	//Calculated
	private double cpu_time;
	private int constraintChecks;
	private int nodesVisited;
	private int backtracks;
	
	
	public PState(String search, String valueOderingHeutistic, String variableOrderingHeuristic, String cspname,
			String solutions, String dynamicity, String printsolutions )	{
		this.search = search;
		this.valueOrderingHeuristic = valueOderingHeutistic;
		this.variableOrderingHeuristics = variableOrderingHeuristic;
		this.cspname = cspname;
		this.setFindsolutions(solutions);
		this.dynamicity = dynamicity;
		this.setPrintsolutions(printsolutions);
		
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getValueOrderingHeuristic() {
		return valueOrderingHeuristic;
	}
	public void setValueOrderingHeuristic(String valueOrderingHeuristic) {
		this.valueOrderingHeuristic = valueOrderingHeuristic;
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
	public String getDynamicity() {
		return dynamicity;
	}
	public void setDynamicity(String dynamicity) {
		this.dynamicity = dynamicity;
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
		return printsolutions;
	}
	public void setPrintsolutions(String printsolutions) {
		this.printsolutions = printsolutions;
	}
	public String getFindsolutions() {
		return findsolutions;
	}
	public void setFindsolutions(String findsolutions) {
		this.findsolutions = findsolutions;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nCSP(-f): " + cspname + "\nSearch(-a): " + search + "\nValue Ordering(-u): "
				+ valueOrderingHeuristic+"\nDynamicity: "+dynamicity+"\n\n");

		sb.append("Constraint-Checks(CC): " + constraintChecks + "\nNodes-Visited(nv): " + nodesVisited + "\nBacktracks(bt): " + backtracks);
		sb.append("\nTime: " + cpu_time + " ms");
		return sb.toString();
	}
}
