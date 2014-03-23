package cspsolver.instance.tools.solver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import cspsolver.instance.components.PConstraint;
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
			System.err.println("Global Constraints are not supported, Only Binary Constraints are supported");
			System.exit(1);
		}
		csp = new PInstance();
		csp.setVariables(parser.getVariables());
		csp.setConstraints(parser.getConstraints());

		for (PVariable variable : csp.getVariables()) {
			variable.setNeighbors(new ArrayList<PVariable>());
			variable.setConstraints(new ArrayList<PConstraint>());

			for (PConstraint constraint : csp.getConstraints()) {
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
		btSearch.setCurrent_domains(new int[csp.getVariables().size() + 1][]);

		btSearch.setFutureForwardChecks(new ArrayList<Stack<Integer>>());
		btSearch.setPastForwardChecks(new ArrayList<Stack<Integer>>());
		btSearch.setReductions(new ArrayList<Stack<Stack<Integer>>>());
		btSearch.setConf_set(new HashMap<Integer, ArrayList<Integer>>());

		for (PVariable variable:csp.getVariables()) {
			btSearch.getCurrentPath().add(variable);
			btSearch.setAssignment(new ArrayList<Integer>());

			btSearch.future_fc[i + 1] = new Stack<Integer>();
			btSearch.past_fc[i + 1] = new Stack<Integer>();
			btSearch.reductions[i + 1] = new Stack<Stack<Integer>>();
			btSearch.past_fc[i + 1].push(0);

			ArrayList<Integer> cset = new ArrayList<Integer>();
			btSearch.getConf_set().put((i + 1), cset);

			btSearch.current_domains[i + 1] = new int[btSearch.cspsolve.vars[i].domain.values.length];
			System.arraycopy(btSearch.cspsolve.vars[i].domain.values, 0, btSearch.current_domains[i + 1], 0,
					btSearch.cspsolve.vars[i].domain.values.length);

			btSearch.current_path[0] = btSearch.cspsolve.vars[0];
			btSearch.unaryC(state, i + 1);
			btSearch.current_path[i + 1].current_domain = new int[btSearch.current_domains[i + 1].length];
			System.arraycopy(btSearch.current_domains[i + 1], 0, btSearch.current_path[i + 1].current_domain, 0,
					btSearch.current_domains[i + 1].length);

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
		btSearch.bcssp("unknown", state.getPrintsolutions(), state.getFindsolutions());
		computetime.Stop();
		state.setCpu_time(computetime.ElapsedTime()*1000);
		state.toString();
		System.out.println("\nSolutions:" + btSearch.Solutions);
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
		// OrderingHeuristics(state.getValueOrderingHeuristic(),state.getVariableOrderingHeuristics(),
		// cspsolver.getCsp());
		cspsolver.init_algorithm(state);
	}
}
