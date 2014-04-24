package cspsolver.instance.components;

import java.util.ArrayList;

public class PInstance {

    private String name;
    private ArrayList<PVariable> variables;
    private ArrayList<PConstraint> constraints;
    private ArrayList<String> orderedVariableNames;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<PVariable > getVariables() {
		return variables;
	}
	public void setVariables(ArrayList<PVariable> vars) {
		this.variables = vars;
	}
	public ArrayList<PConstraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(ArrayList<PConstraint> constraints) {
		this.constraints = constraints;
	}
	public ArrayList<String> getOrderedVariableNames() {
		return orderedVariableNames;
	}
	public void setOrderedVariableNames(ArrayList<String> orderedVariableNames) {
		this.orderedVariableNames = orderedVariableNames;
	}
			
}
