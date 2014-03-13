package cspsolver.instance.intension.logical;

import cspsolver.instance.intension.types.AssociativeType;
import cspsolver.instance.intension.types.SymmetricType;

public class OrEvaluator extends Arity2LogicalEvaluator implements SymmetricType, AssociativeType {
	public void evaluate() {
		top--;
		stack[top] = Math.max(stack[top + 1], stack[top]);
	}
}
