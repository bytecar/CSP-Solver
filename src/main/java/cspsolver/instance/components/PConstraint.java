package cspsolver.instance.components;


public abstract class PConstraint {
	
	protected String name;
	protected PVariable[] scope;
	protected String reference;
	protected String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public PVariable[] getScope() {
		return scope;
	}

	public int getArity() {
		return scope.length;
	}

	public PConstraint(String name, PVariable[] scope2) {
		this.name = name;
		this.scope = scope2;
	}

	public int getMaximalCost() {
		return 1;
	}

	/**
	 * For CSP, returns 0 is the constraint is satisfied and 1 if the constraint
	 * is violated. <br>
	 * For WCSP, returns the cost for the given tuple.
	 */
	public abstract long computeCostOf(int[] tuple);

	public String toString() {
		String s = "  constraint " + name + " with arity = " + scope.length
				+ ", scope = ";
		s += scope[0].getName();
		for (int i = 1; i < scope.length; i++)
			s += " " + scope[i].getName();
		return s;
	}

	public boolean isGuaranteedToBeDivisionByZeroFree() {
		return true;
	}

	public boolean isGuaranteedToBeOverflowFree() {
		return true;
	}
}
