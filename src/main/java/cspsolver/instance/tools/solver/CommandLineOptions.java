package cspsolver.instance.tools.solver;

import org.apache.commons.cli.Options;

public class CommandLineOptions {

	Options options;
	
	public CommandLineOptions()	{
		options = new Options();
		options.addOption("a", "search", true, "Search: BT, CBJ, FC, FCCBJ\n BT: Naive Backtracking\n CBJ:Constraint based Backjumping\nFC: Forward Checking\nFCCBJ: Hybrid of Forward Checking and Constraint based Backjumping, \nFCCBJ is used if not specified\n");
		options.addOption("u", "heuristics", true, "Variable Ordering Heuristic: \nLX(lexicographic), \nLD(least domain), \nDEG(max degree), \nDD(domain degree ratio), \nW(Minimum Width), \nLX is used if not specified\n");
		options.addOption("ud","heuristics-dynamicity", true, "Variable Ordering Heuristic Dynamicity: \n static \n dynamic, \n statically ordered if not specified");
		options.addOption("s", "solution", true, "Solutions: \n\"1\" for single solution, \n\"all\" for all solutions,\n prints all solution if not specified\n");
		options.addOption("f", "input-file", true, "Path to file in XCSP 2.0 format\n");
		options.addOption("p", "print-solutions", true, "Optional: Append \n\"-p p\" to print solutions, \n\"-p n\" not to print solutions,\nprints solution if not specified\n");
		options.addOption("h", "help", false, "Help for using Solver\n");
		options.addOption("d", "debug", false, "CSP debug functionality,\ndisabled if not specified\n");
	}

	public Options getOptions() {
		return options;
	}
	
	
}
	