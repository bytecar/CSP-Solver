package cspsolver.instance.components;

import cspsolver.instance.Toolkit;

public class PPredicate extends PFunction {

	public PPredicate(String name, String formalParametersExpression, String functionalExpression) {
		super(name, formalParametersExpression, functionalExpression);
	}

	public String toString() {
		return "  predicate " + name + " with functional expression = " + functionalExpression + " and (universal) postfix expression = " + Toolkit.buildStringFromTokens(unversalPostfixExpression);
	}

        public String toStrFunc() {
            return Toolkit.buildStringFromTokens(unversalPostfixExpression);
        }
}
