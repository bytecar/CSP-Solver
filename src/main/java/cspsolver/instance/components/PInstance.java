package cspsolver.instance.components;

import java.util.ArrayList;
import java.util.Map;

public class PInstance {

    private String name;
    private ArrayList<PVariable> variables;
    private ArrayList<PConstraint> constraints;
    private ArrayList<String> orderedVariableNames;
    private Map<String, PVariable> mapOfVariables;
    private Map<String, PConstraint> mapOfConstraints;
    private Map<String, PDomain> mapOfDomains;
    
	public Map<String, PVariable> getMapOfVariables() {
		return mapOfVariables;
	}
	public void setMapOfVariables(Map<String, PVariable> mapOfVariables) {
		this.mapOfVariables = mapOfVariables;
	}
	public Map<String, PConstraint> getMapOfConstraints() {
		return mapOfConstraints;
	}
	public void setMapOfConstraints(Map<String, PConstraint> mapOfConstraints) {
		this.mapOfConstraints = mapOfConstraints;
	}
	public Map<String, PDomain> getMapOfDomains() {
		return mapOfDomains;
	}
	public void setMapOfDomains(Map<String, PDomain> mapOfDomains) {
		this.mapOfDomains = mapOfDomains;
	}
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
