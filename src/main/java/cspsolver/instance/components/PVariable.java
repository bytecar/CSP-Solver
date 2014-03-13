package cspsolver.instance.components;


public class PVariable implements Cloneable{
	private String name;

	public PDomain domain;

	public PConstraint[] constraint;

	public PVariable[] neighbors;

	public int numNeighbors;

	public int numConstraints;

	public int[] current_domain;

	public int[] assignment;

	public String getName() {
		return name;
	}

	public Object clone(){
		try{
			PVariable cloned = (PVariable)super.clone();      
			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}

	public PDomain getDomain() {
		return domain;
	}

	public PConstraint[] getConstraints() {
		return constraint;
	}

	public PVariable[] getneighbors() {
		return neighbors;
	}

	public PVariable(String name, PDomain domain) {
		this.name = name;
		this.domain = domain;

	}

	public String toString() {
		return "  variable " + name + " with associated domain " + domain.getName();
	}

	/**
	 * @return the current_domain
	 */
	 public int[] getCurrent_domain() {
		 return current_domain;
	 }

	 public int currentdomlength()   {
		 return current_domain.length;
	 }
	 /**
	  * @return the assignment
	  */
	 public int[] getAssignment() {
		 return assignment;
	 }



}
