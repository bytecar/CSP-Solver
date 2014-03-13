package cspsolver.instance.intension.terminal;

import cspsolver.instance.intension.types.BooleanType;

public class FalseEvaluator extends TerminalEvaluator implements BooleanType{
	public void evaluate() {
		stack[++top] = 0;
	}

}
