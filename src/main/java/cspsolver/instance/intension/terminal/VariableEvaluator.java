package cspsolver.instance.intension.terminal;

import cspsolver.instance.intension.EvaluationManager;
import cspsolver.instance.intension.types.IntegerType;

public class VariableEvaluator extends TerminalEvaluator implements IntegerType{

	private EvaluationManager manager;

	private int position;

	public int getPosition() {
		return position;
	}

	public VariableEvaluator(EvaluationManager manager, int position) {
		this.manager = manager;
		this.position = position;
	}

	public void evaluate() {
		stack[++top] = manager.getCurentValueOf(position);
	}

}
