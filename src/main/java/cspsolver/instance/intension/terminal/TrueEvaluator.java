package cspsolver.instance.intension.terminal;

import cspsolver.instance.intension.types.BooleanType;

public class TrueEvaluator extends TerminalEvaluator implements BooleanType {
	public void evaluate() {
		stack[++top] = 1;
	}

}
