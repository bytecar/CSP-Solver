package cspsolver.instance.components;

public class PInstance {

    public String name;
    public PVariable[] vars;
    public PConstraint[] constraints;
    public String[] OVarName;
			
    public String getname() {
        return name;
    }

    public PVariable[] getvars() {
        return vars;
    };

    public PConstraint[] getconstraints() {
        return constraints;
    }


}
