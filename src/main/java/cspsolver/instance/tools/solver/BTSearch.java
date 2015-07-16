package cspsolver.instance.tools.solver;

public interface BTSearch {
	public int bcssp(String status, PState state);
	public int bt_unlabel(int i, boolean consistent, PState state);
	public int bt_label(int i, boolean consistent, PState state);
	public int getSolutions();
}
