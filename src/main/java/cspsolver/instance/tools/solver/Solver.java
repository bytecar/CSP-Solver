package cspsolver.instance.tools.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.SerializationUtils;

import cspsolver.instance.components.PConstraint;
import cspsolver.instance.components.PDomain;
import cspsolver.instance.components.PExtensionConstraint;
import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PIntensionConstraint;
import cspsolver.instance.components.PVariable;
import cspsolver.instance.tools.InstanceParser;

public class Solver {

	private PInstance csp;
	private InstanceParser parser;
	private PSearch btSearch;

	public PInstance getCsp() {
		return csp;
	}

	public void setCsp(PInstance csp) {
		this.csp = csp;
	}

	public InstanceParser getParser() {
		return parser;
	}

	public void setParser(InstanceParser parser) {
		this.parser = parser;
	}

	public PSearch getBtSearch() {
		return btSearch;
	}

	public void setBtSearch(PSearch btSearch) {
		this.btSearch = btSearch;
	}

	// Update the neighbors and constraints information
	public void updatevars() {

		if (parser.getNbGlobalConstraints() > 0) {
			System.err.println("Global Constraints are not supported, Only Binary and Unary Constraints are supported");
			System.exit(1);
		}
		csp = new PInstance();
		csp.setVariables(parser.getVariables());
		csp.setConstraints(parser.getConstraints());

		for (PVariable variable : csp.getVariables()) {
			variable.setNeighbors(new ArrayList<PVariable>());
			variable.setConstraints(new ArrayList<PConstraint>());
			for (PConstraint constraint : csp.getConstraints()) {
				if (constraint.getArity() == 2) {
					// Writing for binary constraints.
					if (variable.getName().equals(constraint.getScope()[0].getName())) {
						variable.getNeighbors().add(constraint.getScope()[0]);
						variable.getConstraints().add(constraint);
					} else if (variable.getName().equals(constraint.getScope()[1].getName())) {
						variable.getNeighbors().add(constraint.getScope()[1]);
						variable.getConstraints().add(constraint);
					}
				}
			}
		}
	}

	public void debug() {

		System.out.println();
		for (PVariable variable : csp.getVariables()) {
			System.out.println("Variable: " + variable.getName() + "");

			System.out.print("VarConstraints: ");
			for (PConstraint constraint : variable.getConstraints()) {
				System.out.print(" " + constraint.getName());
			}

			System.out.println();
			int[] values = variable.getDomain().getValues();
			System.out.print("Domain: ");

			for (int j = 0; j < values.length; j++) {
				System.out.print(" " + values[j]);
			}

			System.out.println();
			System.out.print("Neighbors: ");
			for (PVariable var : variable.getNeighbors()) {
				System.out.print(" " + var.getName());
			}

			System.out.println("\n");
		}

		for (PConstraint constraint : csp.getConstraints()) {
			System.out.println("Constraint: " + constraint.getName() + "");

			System.out.print("Scope: ");
			for (int k = 0; k < constraint.getArity(); k++) {
				System.out.print(" " + constraint.getScope()[k].getName());
			}

			if ((constraint.getType().equals("conflicts") || constraint.getType().equals("supports"))) {
				System.out.println("\nTuples: "
						+ ((PExtensionConstraint) constraint).getRelation().getStringListOfTuples());
			} else {
				System.out.println("\nFunction: " + ((PIntensionConstraint) constraint).getFunction().toString());
			}
			System.out.println("\n");

		}

	}

	public void init_btSearch(PState state) {

		btSearch.setProblem(csp);
		btSearch.setCurrentPath(new ArrayList<PVariable>());
		btSearch.setCurrent_domains(new ArrayList<int[]>());

		btSearch.setFutureForwardChecks(new ArrayList<Stack<Integer>>());
		btSearch.setPastForwardChecks(new ArrayList<Stack<Integer>>());
		btSearch.setReductions(new ArrayList<Stack<Stack<Integer>>>());
		btSearch.setConf_set(new HashMap<Integer, ArrayList<Integer>>());
		btSearch.setAssignment(new int[csp.getVariables().size() + 1]);

		int i = 0;
		btSearch.getCurrentPath().add(new PVariable("temp", new PDomain("temp", new int[2])));
		ArrayList<int[]> tmp1 = btSearch.getCurrent_domains();
		tmp1.add(new int[1]);
		btSearch.setCurrent_domains(tmp1);
		btSearch.getPastForwardChecks().add(new Stack<Integer>());

		for (PVariable variable : csp.getVariables()) {
			btSearch.getCurrentPath().add(variable);

			btSearch.getFutureForwardChecks().add(new Stack<Integer>());
			btSearch.getPastForwardChecks().add(new Stack<Integer>());
			btSearch.getReductions().add(new Stack<Stack<Integer>>());

			ArrayList<Integer> cset = new ArrayList<Integer>();
			btSearch.getConf_set().put((i + 1), cset);

			ArrayList<int[]> tmp = btSearch.getCurrent_domains();
			tmp.add(variable.getDomain().getValues());
			btSearch.setCurrent_domains(tmp);

			btSearch.getCurrentPath().set(0, btSearch.getProblem().getVariables().get(0));
			btSearch.unaryC(state, i + 1);
			btSearch.getCurrentPath()
					.get(i + 1)
					.setCurrent_domain(
							(PDomain) SerializationUtils.clone(btSearch.getCurrentPath().get(i + 1).getDomain()));
			btSearch.getCurrentPath().get(i + 1).getCurrent_domain()
					.setValues(btSearch.getCurrent_domains().get(i + 1));

			i++;
		}

		for (int k = 1; k <= csp.getVariables().size(); k++) {
			btSearch.getPastForwardChecks().get(k).push(0);
		}

	}

	void init_algorithm(PState state) {

		String alg = state.getSearch();

		if (alg.equalsIgnoreCase("CBJ")) {
			btSearch = new PSearchCBJ();
		} else if (alg.equalsIgnoreCase("FCCBJ")) {
			btSearch = new PSearchFCCBJ();
		} else if (alg.equalsIgnoreCase("BT")) {
			btSearch = new PSearchVanilla();
		} else if (alg.equalsIgnoreCase("FC")) {
			btSearch = new PSearchFC();
		}

		timer computetime = new timer();
		computetime.Start();
		this.init_btSearch(state);
		btSearch.bcssp("unknown", state);
		computetime.Stop();
		state.setCpu_time(computetime.ElapsedTime());
		System.out.println(state.toString());
		System.out.println("\nSolutions:" + btSearch.getSolutions());
	}

	public static void main(String[] args) {

		CommandLineParser parser = new GnuParser();
		String search = null, heuristics = null, inputfile = null, printsolutions = null, findsolutions = null;

		// Command Line Options parsing begins here
		try {

			Options options = new CommandLineOptions().getOptions();
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("CSP-Solver", options, true);
				System.exit(0);
				;
			}
			if (cmd.hasOption("search")) {
				search = cmd.getOptionValue("search");
			} else {
				search = "FCCBJ";
			}

			if (cmd.hasOption("heuristics")) {
				heuristics = cmd.getOptionValue("heuristics");
			} else {
				heuristics = "LX";
			}

			if (cmd.hasOption("inputfile")) {
				inputfile = cmd.getOptionValue("inputfile");
			} else {
				System.err.println("XCSP 2.0 input file not specified");
				System.exit(1);
			}

			if (cmd.hasOption("printsolutions")) {
				printsolutions = cmd.getOptionValue("printsolutions");
			} else {
				printsolutions = "p";
			}

			if (cmd.hasOption("solution")) {
				findsolutions = cmd.getOptionValue("solution");
			} else {
				findsolutions = "all";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Command Line options parsing Ends here
		Solver cspsolver = new Solver();
		cspsolver.setParser(new InstanceParser());
		cspsolver.getParser().loadInstance(inputfile);
		cspsolver.getParser().parse(false);

		// Preprocess and populate search Object
		cspsolver.updatevars();
		PState state = new PState(search, "Unspecified", heuristics, inputfile, findsolutions, "Unspecified",
				printsolutions);

		// new
		new OrderingHeuristics(state.getVariableOrderingHeuristics(), state.getValueOrderingHeuristic(),
				cspsolver.getCsp(), cspsolver.getParser());
		cspsolver.init_algorithm(state);
	}
}
