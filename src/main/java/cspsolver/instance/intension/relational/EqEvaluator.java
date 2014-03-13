package cspsolver.instance.intension.relational;

import cspsolver.instance.intension.types.AssociativeType;
import cspsolver.instance.intension.types.SymmetricType;

public class EqEvaluator extends RelationalEvaluator implements SymmetricType, AssociativeType {
	public void evaluate() {
		top--;
		stack[top] = (stack[top + 1] == stack[top] ? 1 : 0);
	}
}
