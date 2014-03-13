package cspsolver.instance.intension.logical;

import cspsolver.instance.intension.types.AssociativeType;
import cspsolver.instance.intension.types.SymmetricType;

public class AndEvaluator extends Arity2LogicalEvaluator implements SymmetricType, AssociativeType {
	public void evaluate() {
		top--;
		stack[top] = Math.min(stack[top + 1], stack[top]);
	}
}
