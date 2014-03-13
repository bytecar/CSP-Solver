package cspsolver.instance.intension.arithmetic;

import cspsolver.instance.intension.types.AssociativeType;
import cspsolver.instance.intension.types.SymmetricType;

public class MinEvaluator extends Arity2ArithmeticEvaluator implements SymmetricType, AssociativeType {
	public void evaluate() {
		top--;
		stack[top] = Math.min(stack[top + 1], stack[top]);
	}
}
