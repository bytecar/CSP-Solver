package cspsolver.instance.tools.solver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import cspsolver.instance.components.PConstraint;
import cspsolver.instance.components.PInstance;
import cspsolver.instance.components.PVariable;
import cspsolver.instance.tools.InstanceParser;


public class Solver {

	private PInstance csp;
	private int nbCons;
	private int nbVars;
	private InstanceParser parser;
	private PSearch btSearch;

	// Update the neighbors and constraints information
	public void updatevars() {

		csp = new PInstance();
		// csp.vars=new PVariable[nbVars];
		// csp.constraints=new PConstraint[nbCons];
		csp.vars = parser.variables;
		csp.constraints = parser.constraints;
		// System.arraycopy(variables, 0, csp.vars, 0, nbVars);
		// System.arraycopy(constraints, 0, csp.constraints, 0, nbCons);
		nbCons = csp.constraints.length;
		nbVars = csp.vars.length;
		String varname;
		PVariable[] scope_vars;

		for (int k = 0; k < nbVars; k++) {
			csp.vars[k].neighbors = new PVariable[nbVars];
			csp.vars[k].constraint = new PConstraint[nbCons];

			int neighbors_idx = 0;
			int constraint_idx = 0;

			for (int j = 0; j < nbCons; j++) {
				// Update relation kind
				if ((csp.constraints[j].type.equals("conflicts") || csp.constraints[j].type
						.equals("supports"))) {
					csp.constraints[j].relation = parser.mapOfRelations
							.get(csp.constraints[j].reference);
				} else {
					if (csp.constraints[j].reference
							.equals("global:allDifferent")) {
						System.out
								.println("Global Constraints not supported: Only Binary");
						System.exit(1);
					} else {
						csp.constraints[j].expression = parser.mapOfPredicates
								.get(csp.constraints[j].reference);
					}
				}

				varname = csp.vars[k].getName();
				// scope_vars=new PVariable[scopelen];
				scope_vars = csp.constraints[j].getScope();
				// System.out.print("\t"+scope_vars[1].getName()+"\t");
				int temp = 0;

				if (varname.equals(scope_vars[0].getName())) {
					temp = 1;
				}
				// for(int i=0;i<scopelen;i++) {
				if (scope_vars.length == 2) {
					if (varname.equals(scope_vars[0].getName())
							|| varname.equals(scope_vars[1].getName())) {

						csp.vars[k].constraint[constraint_idx] = csp.constraints[j];
						constraint_idx++;

						if (temp == 0) {
							csp.vars[k].neighbors[neighbors_idx] = scope_vars[0];
							neighbors_idx++;
						} else {
							csp.vars[k].neighbors[neighbors_idx] = scope_vars[1];
							neighbors_idx++;
							// }
						}
					} else {
						if (temp == 1) {
							csp.vars[k].neighbors[neighbors_idx] = scope_vars[0];
							neighbors_idx++;
						}
					}

				} else {
					if (varname.equals(scope_vars[0].getName())) {
						csp.vars[k].constraint[constraint_idx] = csp.constraints[j];
						constraint_idx++;
					}
				}
				csp.vars[k].numNeighbors = neighbors_idx;
				csp.vars[k].numConstraints = constraint_idx;

				// System.out.println(csp.vars[k].numNeighbors+"<- neighbor constraint->"+csp.vars[k].numConstraints);
			}
		}
	}

	public void debug() {

		System.out.println();
		for (int i = 0; i < nbVars; i++) {
			System.out.println("Variable: " + csp.vars[i].getName() + "");

			System.out.print("VarConstraints: ");
			for (int k = 0; k < csp.vars[i].numConstraints; k++) {
				System.out.print(" " + csp.vars[i].constraint[k].getName());
			}

			System.out.println();
			int[] values = csp.vars[i].getDomain().getValues();
			System.out.print("Domain: ");

			for (int j = 0; j < values.length; j++) {
				System.out.print(" " + values[j]);
			}

			System.out.println();
			System.out.print("Neighbors: ");
			for (int k = 0; k < csp.vars[i].numNeighbors; k++) {
				System.out.print(" " + csp.vars[i].neighbors[k].getName());
			}

			System.out.println("\n");
		}

		for (int i = 0; i < nbCons; i++) {
			System.out.println("Constraint: " + csp.constraints[i].getName()
					+ "");

			System.out.print("Scope: ");
			for (int k = 0; k < csp.constraints[i].getArity(); k++) {
				System.out.print(" " + csp.constraints[i].scope[k].getName());
			}

			if ((csp.constraints[i].type.equals("conflicts") || csp.constraints[i].type
					.equals("supports"))) {
				System.out.println();
				System.out.println("Tuples: "
						+ csp.constraints[i].relation.toStrTuples());
			} else {
				System.out.println();
				System.out.println("Function: "
						+ csp.constraints[i].expression.toStrFunc());
			}
			System.out.println("\n");

		}

	}

	public void init_btSearch(PState state) {

		btSearch.setCspsolve(csp);
		btSearch.current_path = new PVariable[nbVars + 1];
		btSearch.current_domains = new int[nbVars + 1][];

		btSearch.future_fc = (Stack<Integer>[]) Array.newInstance(Stack.class,
				nbVars + 1);
		btSearch.past_fc = (Stack<Integer>[]) Array.newInstance(Stack.class,
				nbVars + 1);
		btSearch.reductions = (Stack<Stack<Integer>>[]) Array.newInstance(
				Stack.class, nbVars + 1);
		btSearch.conf_set = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0; i < nbVars; i++) {
			btSearch.current_path[i + 1] = btSearch.cspsolve.vars[i];
			btSearch.assignment = new int[nbVars + 1];

			btSearch.future_fc[i + 1] = new Stack<Integer>();
			btSearch.past_fc[i + 1] = new Stack<Integer>();
			btSearch.reductions[i + 1] = new Stack<Stack<Integer>>();
			btSearch.past_fc[i + 1].push(0);

			ArrayList<Integer> cset = new ArrayList<Integer>();
			btSearch.conf_set.put((i + 1), cset);

			btSearch.current_domains[i + 1] = new int[btSearch.cspsolve.vars[i].domain.values.length];
			System.arraycopy(btSearch.cspsolve.vars[i].domain.values, 0,
					btSearch.current_domains[i + 1], 0,
					btSearch.cspsolve.vars[i].domain.values.length);

			btSearch.current_path[0] = btSearch.cspsolve.vars[0];
			btSearch.unaryC(state, i + 1);
			btSearch.current_path[i + 1].current_domain = new int[btSearch.current_domains[i + 1].length];
			System.arraycopy(btSearch.current_domains[i + 1], 0,
					btSearch.current_path[i + 1].current_domain, 0,
					btSearch.current_domains[i + 1].length);

		}

	}

	void init_algorithm(String alg, PState state, String printing,
			String numOfSolutions) {

		if (alg.equalsIgnoreCase("CBJ")) {
			btSearch = new PSearchCBJ();
		} else if (alg.equalsIgnoreCase("FCCBJ")) {
			btSearch = new PSearchFCCBJ();
		} else if (alg.equalsIgnoreCase("BT")) {
			btSearch = new PSearchVanilla();
		} else if (alg.equalsIgnoreCase("FC")) {
			btSearch = new PSearchFC();
		}
		this.init_btSearch(state);
		btSearch.bcssp("unknown", state, printing, numOfSolutions);
		state.OutString();
		System.out.println("\nSolutions:" + btSearch.Solutions);
	}

	public static void main(String[] args) {

		CommandLineParser parser = new GnuParser();
		String search = null, heuristics = null, inputfile = null, printsolutions = null, solution = null;
		// Command Line Options parsing begins here
		try {

			Options options = new CommandLineOptions().getOptions();
			CommandLine cmd = parser.parse(options,args);

			if(cmd.hasOption("help"))	{
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "CSP-Solver", options, true );
				System.exit(0);;
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
				inputfile = "src/main/resources/4qc.xml";
			}

			if (cmd.hasOption("printsolutions")) {
				printsolutions = cmd.getOptionValue("printsolutions");
			} else {
				printsolutions = "p";
			}

			if (cmd.hasOption("solution")) {
				solution = cmd.getOptionValue("solution");
			} else {
				solution = "all";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Command Line options parsing Ends here

		Solver cspsolver = new Solver();
		cspsolver.parser = new InstanceParser();
		cspsolver.parser.loadInstance(inputfile);
		cspsolver.parser.parse(false);

		timer computetime = new timer();
		cspsolver.updatevars();

		PState state = new PState(search,heuristics, inputfile);

		new OHeuristics(heuristics, cspsolver.csp,cspsolver.parser);

		computetime.Start();
		cspsolver.init_algorithm(search, state, printsolutions, solution);
		computetime.Stop();

		System.out.println("\nTime: " + computetime.ElapsedTime() * 1000
				+ " ms");
		// cspsolver.debug();

	}
}
