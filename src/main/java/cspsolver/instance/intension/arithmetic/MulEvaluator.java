package cspsolver.instance.intension.arithmetic;

import cspsolver.instance.intension.types.AssociativeType;
import cspsolver.instance.intension.types.SymmetricType;

public class MulEvaluator extends Arity2ArithmeticEvaluator implements SymmetricType, AssociativeType {
	public void evaluate() {
		top--;
		stack[top] = stack[top + 1] * stack[top];
	}
}
