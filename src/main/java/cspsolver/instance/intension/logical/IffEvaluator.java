package cspsolver.instance.intension.logical;

import cspsolver.instance.intension.types.AssociativeType;
import cspsolver.instance.intension.types.SymmetricType;

public class IffEvaluator extends Arity2LogicalEvaluator implements SymmetricType, AssociativeType {
	public void evaluate() {
		top--;
		stack[top] = (stack[top + 1] == stack[top] ? 1 : 0);
	}
}
