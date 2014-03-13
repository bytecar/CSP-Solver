package cspsolver.instance.tools.solver;

import org.apache.commons.cli.Options;

public class CommandLineOptions {

	Options options;
	
	public CommandLineOptions()	{
		options = new Options();
		options.addOption("a", "search", true, "Search: BT, CBJ, FC, FCCBJ");
		options.addOption("u", "heuristics", true, "Variable Ordering Heuristic: LX(lexicographic), LD(least domain), DEG(max degree), DD(domain degree ratio), W(Minimum Width)");
		options.addOption("s", "solution", true, "Solutions: \"1\" for single solution, \"all\" for all solutions");
		options.addOption("f", "inputfile", true, "Path to file in XCSP 2.0 format");
		options.addOption("p", "printsolutions", true, "Optional: Append \"-p p\" to print solutions, \"-p n\" not to print solutions");
		options.addOption("h", "help", false, "Help for using Solver");
	}

	public Options getOptions() {
		return options;
	}
	
	
}
	