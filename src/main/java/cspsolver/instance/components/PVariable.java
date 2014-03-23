package cspsolver.instance.components;

import java.util.ArrayList;


public class PVariable {
	
	private String name;
	private PDomain domain;
	private ArrayList<PConstraint> constraints;
	private ArrayList<PVariable> neighbors;
	private PDomain current_domain;
	
	public PVariable(String name, PDomain initial_domain)	{
		this.name = name;
		this.setDomain(initial_domain);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public  ArrayList<PConstraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(ArrayList<PConstraint> constraint) {
		this.constraints = constraint;
	}
	public ArrayList<PVariable> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(ArrayList<PVariable> neighbors) {
		this.neighbors = neighbors;
	}
	public PDomain getCurrent_domain() {
		return current_domain;
	}
	public void setCurrent_domain(PDomain current_domain) {
		this.current_domain = current_domain;
	}
	public PDomain getDomain() {
		return domain;
	}
	public void setDomain(PDomain domain) {
		this.domain = domain;
	}

}
