package cspsolver.instance.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cspsolver.instance.InstanceTokens;
import cspsolver.instance.PredicateTokens.RelationalOperator;
import cspsolver.instance.Toolkit;
import cspsolver.instance.XMLManager;
import cspsolver.instance.components.PAllDifferent;
import cspsolver.instance.components.PConstraint;
import cspsolver.instance.components.PCumulative;
import cspsolver.instance.components.PDomain;
import cspsolver.instance.components.PElement;
import cspsolver.instance.components.PExtensionConstraint;
import cspsolver.instance.components.PFunction;
import cspsolver.instance.components.PIntensionConstraint;
import cspsolver.instance.components.PPredicate;
import cspsolver.instance.components.PRelation;
import cspsolver.instance.components.PSoftRelation;
import cspsolver.instance.components.PVariable;
import cspsolver.instance.components.PWeightedSum;
import cspsolver.instance.components.Task;

public class InstanceParser {

    private static final String VERSION = "Version 0.5.1, July 2015 - By Kartik Vedalaveni (XCSP 2.0 Parser adapted from abscon) ";
    private Document document;
    private String type;
    private String format;
    private int maxConstraintArity;
    private Map<String, PDomain> mapOfDomains;
    private Map<String, PVariable> mapOfVariables;
    private Map<String, PRelation> mapOfRelations;
    private Map<String, PFunction> mapOfFunctions;
    private Map<String, PPredicate> mapOfPredicates;
    private Map<String, PConstraint> mapOfConstraints;
    private String tuples_type;
    private ArrayList<PVariable> variables;
    private int nbVars;
    private int nbCons;
    private ArrayList<PConstraint> constraints;
    private ArrayList<PRelation> relations;
	private String constraint_reference;
    private int nbExtensionConstraints;
    private int nbIntensionConstraints;
    private int nbGlobalConstraints;
    private String satisfiable;
    private String minViolatedConstraints;    
    public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Map<String, PDomain> getMapOfDomains() {
		return mapOfDomains;
	}

	public void setMapOfDomains(Map<String, PDomain> mapOfDomains) {
		this.mapOfDomains = mapOfDomains;
	}

	public Map<String, PVariable> getMapOfVariables() {
		return mapOfVariables;
	}

	public void setMapOfVariables(Map<String, PVariable> mapOfVariables) {
		this.mapOfVariables = mapOfVariables;
	}

	public Map<String, PRelation> getMapOfRelations() {
		return mapOfRelations;
	}

	public void setMapOfRelations(Map<String, PRelation> mapOfRelations) {
		this.mapOfRelations = mapOfRelations;
	}

	public Map<String, PFunction> getMapOfFunctions() {
		return mapOfFunctions;
	}

	public void setMapOfFunctions(Map<String, PFunction> mapOfFunctions) {
		this.mapOfFunctions = mapOfFunctions;
	}

	public Map<String, PPredicate> getMapOfPredicates() {
		return mapOfPredicates;
	}

	public void setMapOfPredicates(Map<String, PPredicate> mapOfPredicates) {
		this.mapOfPredicates = mapOfPredicates;
	}

	public ArrayList<PConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(ArrayList<PConstraint> constraints) {
		this.constraints = constraints;
	}

	public ArrayList<PRelation> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<PRelation> relations) {
		this.relations = relations;
	}

	public String getConstraint_reference() {
		return constraint_reference;
	}

	public void setConstraint_reference(String constraint_reference) {
		this.constraint_reference = constraint_reference;
	}

	public boolean isDisplayInstance() {
		return displayInstance;
	}

	public void setDisplayInstance(boolean displayInstance) {
		this.displayInstance = displayInstance;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMaxConstraintArity(int maxConstraintArity) {
		this.maxConstraintArity = maxConstraintArity;
	}

	public void setMapOfConstraints(Map<String, PConstraint> mapOfConstraints) {
		this.mapOfConstraints = mapOfConstraints;
	}

	public void setVariables(ArrayList<PVariable> variables) {
		this.variables = variables;
	}

	public void setNbExtensionConstraints(int nbExtensionConstraints) {
		this.nbExtensionConstraints = nbExtensionConstraints;
	}

	public void setNbIntensionConstraints(int nbIntensionConstraints) {
		this.nbIntensionConstraints = nbIntensionConstraints;
	}

	public void setNbGlobalConstraints(int nbGlobalConstraints) {
		this.nbGlobalConstraints = nbGlobalConstraints;
	}

	public void setSatisfiable(String satisfiable) {
		this.satisfiable = satisfiable;
	}

	public void setMinViolatedConstraints(String minViolatedConstraints) {
		this.minViolatedConstraints = minViolatedConstraints;
	}

    public String getType() {
        return type;
    }

    public ArrayList<PVariable> getVariables() {
        return variables;
    }

    public int getMaxConstraintArity() {
        return maxConstraintArity;
    }

    public Map<String, PConstraint> getMapOfConstraints() {
        return mapOfConstraints;
    }

    public int getNbExtensionConstraints() {
        return nbExtensionConstraints;
    }

    public int getNbIntensionConstraints() {
        return nbIntensionConstraints;
    }

    public int getNbGlobalConstraints() {
        return nbGlobalConstraints;
    }

    public String getConstraintsCategory() {
        return (nbExtensionConstraints > 0 ? "E" : "") + (nbIntensionConstraints > 0 ? "I" : "") + (nbGlobalConstraints > 0 ? "G" : "");
    }

    public String getSatisfiable() {
        return satisfiable;
    }

    public String getMinViolatedConstraints() {
        return minViolatedConstraints;
    }

    private boolean displayInstance = true;

    public void loadInstance(String fileName) {
        document = XMLManager.load(fileName);
    }

    private void parsePresentation(Element presentationElement) {
        String s = presentationElement.getAttribute(InstanceTokens.MAX_CONSTRAINT_ARITY.trim());
        maxConstraintArity = s.length() == 0 || s.equals("?") ? -1 : Integer.parseInt(s);
        type = presentationElement.getAttribute(InstanceTokens.TYPE.trim());
        type = type.length() == 0 || type.equals("?") ? InstanceTokens.CSP : type;
        format = presentationElement.getAttribute(InstanceTokens.FORMAT.trim());
        if (displayInstance) {
            System.out.println("Instance with maxConstraintArity=" + maxConstraintArity + " type=" + type + " format=" + format);
        }
        s = presentationElement.getAttribute(InstanceTokens.NB_SOLUTIONS).trim();
        satisfiable = s.length() == 0 || s.equals("?") ? "unknown" : s.equals("0") ? "false" : "true";
        s = presentationElement.getAttribute(InstanceTokens.MIN_VIOLATED_CONSTRAINTS).trim();
        minViolatedConstraints = satisfiable.equals("true") ? "0" : s.length() == 0 || s.equals("?") ? "unknown" : s;
    }

    private int[] parseDomainValues(int nbValues, String stringOfValues) {
        int cnt = 0;
        int[] values = new int[nbValues];
        StringTokenizer st = new StringTokenizer(stringOfValues);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            int position = token.indexOf(InstanceTokens.DISCRETE_INTERVAL_SEPARATOR);
            if (position == -1) {
                values[cnt++] = Integer.parseInt(token);
            } else {
                int min = Integer.parseInt(token.substring(0, position));
                int max = Integer.parseInt(token.substring(position + InstanceTokens.DISCRETE_INTERVAL_SEPARATOR.length()));
                for (int j = min; j <= max; j++) {
                    values[cnt++] = j;
                }
            }
        }
        return values;
    }

    private PDomain parseDomain(Element domainElement) {
        String name = domainElement.getAttribute(InstanceTokens.NAME);
        int nbValues = Integer.parseInt(domainElement.getAttribute(InstanceTokens.NB_VALUES));
        int[] values = parseDomainValues(nbValues, domainElement.getTextContent());
        if (nbValues != values.length) {
            throw new RuntimeException();
        }
        return new PDomain(name, values);
    }

    private void parseDomains(Element domainsElement) {
        mapOfDomains = new HashMap<String, PDomain>();
        int nbDomains = Integer.parseInt(domainsElement.getAttribute(InstanceTokens.NB_DOMAINS));
        if (displayInstance) {
            System.out.println("=> " + nbDomains + " domains");
        }

        NodeList nodeList = domainsElement.getElementsByTagName(InstanceTokens.DOMAIN);
        for (int i = 0; i < nodeList.getLength(); i++) {
            PDomain domain = parseDomain((Element) nodeList.item(i));
            mapOfDomains.put(domain.getName(), domain);
            if (displayInstance) {
                System.out.println(domain);
            }
        }
    }

    private PVariable parseVariable(Element variableElement) {
        String name = variableElement.getAttribute(InstanceTokens.NAME);
        String domainName = variableElement.getAttribute(InstanceTokens.DOMAIN);
        return new PVariable(name, mapOfDomains.get(domainName));
    }

    private void parseVariables(Element variablesElement) {
        mapOfVariables = new HashMap<String, PVariable>();
        int nbVariables = Integer.parseInt(variablesElement.getAttribute(InstanceTokens.NB_VARIABLES));
        setNbVars(nbVariables);
        if (displayInstance) {
            System.out.println("=> " + nbVariables + " variables");
        }

        variables = new ArrayList<PVariable>();
        NodeList nodeList = variablesElement.getElementsByTagName(InstanceTokens.VARIABLE);
        for (int i = 0; i < nodeList.getLength(); i++) {
            PVariable variable = parseVariable((Element) nodeList.item(i));
            mapOfVariables.put(variable.getName(), variable);
            variables.add(variable);
            if (displayInstance) {
                System.out.println(variable);
            }
        }
    }

    private PRelation parseRelationTuples(String name, int arity, int nbTuples, String semantics, String textContent) {
        int[][] tuples = new int[nbTuples][arity];
        StringTokenizer st = new StringTokenizer(textContent, InstanceTokens.WHITE_SPACE + InstanceTokens.TUPLES_SEPARATOR);


        for (int i = 0; i < tuples.length; i++) {
            for (int j = 0; j < arity; j++) {
                tuples[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        return new PRelation(name, arity, nbTuples, semantics, tuples);
    }

    private PRelation parseSoftRelationTuples(String name, int arity, int nbTuples, String semantics, String textContent, String textDefaultCost) {
        int[][] tuples = new int[nbTuples][arity];
        int[] weights = new int[nbTuples];
        StringTokenizer st = new StringTokenizer(textContent, InstanceTokens.WHITE_SPACE + InstanceTokens.TUPLES_SEPARATOR);
        int currentCost = -2;
        for (int i = 0; i < nbTuples; i++) {
            String token = st.nextToken();
            int costFlagPosition = token.lastIndexOf(InstanceTokens.COST_SEPARATOR);
            if (costFlagPosition != -1) {
                currentCost = Integer.parseInt(token.substring(0, costFlagPosition));
                token = token.substring(costFlagPosition + 1);
            }
            weights[i] = currentCost;
            tuples[i][0] = Integer.parseInt(token);
            for (int j = 1; j < arity; j++) {
                tuples[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int defaultCost = textDefaultCost.equals(InstanceTokens.INFINITY) ? Integer.MAX_VALUE : Integer.parseInt(textDefaultCost);
        return new PSoftRelation(name, arity, nbTuples, semantics, tuples, weights, defaultCost);
    }

    private PRelation parseRelation(Element relationElement) {
        String name = relationElement.getAttribute(InstanceTokens.NAME);
        int arity = Integer.parseInt(relationElement.getAttribute(InstanceTokens.ARITY));
        int nbTuples = Integer.parseInt(relationElement.getAttribute(InstanceTokens.NB_TUPLES));
        String semantics = relationElement.getAttribute(InstanceTokens.SEMANTICS);
        tuples_type = semantics;
        if (semantics.equals(InstanceTokens.SOFT)) {
            return parseSoftRelationTuples(name, arity, nbTuples, semantics, relationElement.getTextContent(), relationElement.getAttribute(InstanceTokens.DEFAULT_COST));
        } else {
            return parseRelationTuples(name, arity, nbTuples, semantics, relationElement.getTextContent());
        }
    }

    private void parseRelations(Element relationsElement) {
        mapOfRelations = new HashMap<String, PRelation>();
        if (relationsElement == null) {
            return;
        }
        int nbRelations = Integer.parseInt(relationsElement.getAttribute(InstanceTokens.NB_RELATIONS));
        if (displayInstance) {
            System.out.println("=> " + nbRelations + " relations");
        }

        relations = new ArrayList<PRelation>();
        NodeList nodeList = relationsElement.getElementsByTagName(InstanceTokens.RELATION);
        for (int i = 0; i < nodeList.getLength(); i++) {
            PRelation relation = parseRelation((Element) nodeList.item(i));
            relations.add(relation);
            mapOfRelations.put(relation.getName(), relation);
            if (displayInstance) {
                System.out.println(relation);
            }
        }
    }

    private PPredicate parsePredicate(Element predicateElement) {
        String name = predicateElement.getAttribute(InstanceTokens.NAME);
        Element parameters = (Element) predicateElement.getElementsByTagName(InstanceTokens.PARAMETERS).item(0);
        Element expression = (Element) predicateElement.getElementsByTagName(InstanceTokens.EXPRESSION).item(0);
        Element functional = (Element) expression.getElementsByTagName(InstanceTokens.FUNCTIONAL).item(0);
        return new PPredicate(name, parameters.getTextContent().trim(), functional.getTextContent().trim());
    }

    private void parsePredicates(Element predicatesElement) {
        mapOfPredicates = new HashMap<String, PPredicate>();
        if (predicatesElement == null) {
            return;
        }
        int nbPredicates = Integer.parseInt(predicatesElement.getAttribute(InstanceTokens.NB_PREDICATES));
        if (displayInstance) {
            System.out.println("=> " + nbPredicates + " predicates");
        }

        tuples_type = "intension";

        NodeList nodeList = predicatesElement.getElementsByTagName(InstanceTokens.PREDICATE);
        for (int i = 0; i < nodeList.getLength(); i++) {
            PPredicate predicate = parsePredicate((Element) nodeList.item(i));
            mapOfPredicates.put(predicate.getName(), predicate);
            if (displayInstance) {
                System.out.println(predicate);
            }
        }
    }

    private PFunction parseFunction(Element functionElement) {
        String name = functionElement.getAttribute(InstanceTokens.NAME);
        Element parameters = (Element) functionElement.getElementsByTagName(InstanceTokens.PARAMETERS).item(0);
        Element expression = (Element) functionElement.getElementsByTagName(InstanceTokens.EXPRESSION).item(0);
        Element functional = (Element) expression.getElementsByTagName(InstanceTokens.FUNCTIONAL).item(0);
        return new PFunction(name, parameters.getTextContent(), functional.getTextContent());
    }

    private void parseFunctions(Element functionsElement) {
        mapOfFunctions = new HashMap<String, PFunction>();
        if (functionsElement == null) {
            return;
        }
        int nbFunctions = Integer.parseInt(functionsElement.getAttribute(InstanceTokens.NB_FUNCTIONS));
        if (displayInstance) {
            System.out.println("=> " + nbFunctions + " functions");
        }

        NodeList nodeList = functionsElement.getElementsByTagName(InstanceTokens.FUNCTION);
        for (int i = 0; i < nodeList.getLength(); i++) {
            PFunction function = parseFunction((Element) nodeList.item(i));
            mapOfFunctions.put(function.getName(), function);
            if (displayInstance) {
                System.out.println(function);
            }
        }
    }

    private PVariable[] parseScope(String scope) {
        StringTokenizer st = new StringTokenizer(scope, " ");
        PVariable[] involvedVariables = new PVariable[st.countTokens()];
        for (int i = 0; i < involvedVariables.length; i++) {
            involvedVariables[i] = mapOfVariables.get(st.nextToken());
        }
        return involvedVariables;
    }

    private int searchIn(String s, PVariable[] t) {
        for (int i = 0; i < t.length; i++) {
            if (t[i].getName().equals(s)) {
                return i;
            }
        }
        return -1;
    }

    private PConstraint parseElementConstraint(String name, PVariable[] scope, Element parameters) {
        StringTokenizer st = new StringTokenizer(Toolkit.insertWhitespaceAround(parameters.getTextContent().trim(), InstanceTokens.BRACKETS), InstanceTokens.WHITE_SPACE);
        PVariable index = mapOfVariables.get(st.nextToken()); // index is necessarily a variable
        st.nextToken(); // token [ skipped
        List<Object> table = new ArrayList<Object>();
        String token = st.nextToken();
        while (!token.equals("]")) {
            Object object = mapOfVariables.get(token);
            if (object == null) {
                object = Integer.parseInt(token);
            }
            table.add(object);
            token = st.nextToken();
        }
        token = st.nextToken();
        Object value = mapOfVariables.get(token);
        if (value == null) {
            value = Integer.parseInt(token);
        }
        return new PElement(name, scope, index, table.toArray(new Object[table.size()]), value);
    }

    private PConstraint parseWeightedSumConstraint(String name, PVariable[] scope, Element parameters) {
        NodeList nodeList = parameters.getChildNodes();
        StringTokenizer st = new StringTokenizer(nodeList.item(0).getTextContent(), InstanceTokens.WHITE_SPACE + "[{}]");
        int[] coeffs = new int[scope.length];
        while (st.hasMoreTokens()) {
            int coeff = Integer.parseInt(st.nextToken());
            int position = searchIn(st.nextToken(), scope);
            coeffs[position] += coeff;
        }
        RelationalOperator operator = RelationalOperator.getRelationalOperatorFor(nodeList.item(1).getNodeName());
        int limit = Integer.parseInt(nodeList.item(2).getTextContent().trim());
        return new PWeightedSum(name, scope, coeffs, operator, limit);
    }

    private String buildStringRepresentationOf(Element parameters) {
        NodeList nodeList = parameters.getChildNodes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals(InstanceTokens.NIL)) {
                sb.append(" ").append(InstanceTokens.NIL).append(" ");
            } else {
                sb.append(Toolkit.insertWhitespaceAround(node.getTextContent(), InstanceTokens.BRACKETS));
            }
        }
        return sb.toString();
    }

    private PConstraint parseCumulativeConstraint(String name, PVariable[] scope, Element parameters) {
        StringTokenizer st = new StringTokenizer(buildStringRepresentationOf(parameters), InstanceTokens.WHITE_SPACE + "{}");
        st.nextToken(); // token '[' skipped
        List<Task> tasks = new ArrayList<Task>();
        String token = st.nextToken();
        while (!token.equals("]")) {
            Object origin = mapOfVariables.get(token);
            if (origin == null) {
                origin = token.equals(InstanceTokens.NIL) ? null : Integer.parseInt(token);
            }
            token = st.nextToken();
            Object duration = mapOfVariables.get(token);
            if (duration == null) {
                duration = token.equals(InstanceTokens.NIL) ? null : Integer.parseInt(token);
            }
            token = st.nextToken();
            Object end = mapOfVariables.get(token);
            if (end == null) {
                end = token.equals(InstanceTokens.NIL) ? null : Integer.parseInt(token);
            }
            token = st.nextToken();
            Object height = mapOfVariables.get(token);
            if (height == null) {
                height = Integer.parseInt(token);
            }
            tasks.add(new Task(origin, duration, end, height));
            token = st.nextToken();
        }
        int limit = Integer.parseInt(st.nextToken());
        return new PCumulative(name, scope, tasks.toArray(new Task[tasks.size()]), limit);
    }

    private PConstraint parseConstraint(Element constraintElement) {
        String name = constraintElement.getAttribute(InstanceTokens.NAME);
        int arity = Integer.parseInt(constraintElement.getAttribute(InstanceTokens.ARITY));
        if (arity > maxConstraintArity) {
            maxConstraintArity = arity;
        }
        PVariable[] scope = parseScope(constraintElement.getAttribute(InstanceTokens.SCOPE));

        String reference = constraintElement.getAttribute(InstanceTokens.REFERENCE);
        constraint_reference = reference;
        if (mapOfRelations.containsKey(reference)) {
            nbExtensionConstraints++;
            return new PExtensionConstraint(name, scope, mapOfRelations.get(reference));
        }

        if (mapOfFunctions.containsKey(reference)) {
            Element parameters = (Element) constraintElement.getElementsByTagName(InstanceTokens.PARAMETERS).item(0);
            return new PIntensionConstraint(name, scope, mapOfFunctions.get(reference), parameters.getTextContent());
        }

        if (mapOfPredicates.containsKey(reference)) {
            Element parameters = (Element) constraintElement.getElementsByTagName(InstanceTokens.PARAMETERS).item(0);
            nbIntensionConstraints++;
            return new PIntensionConstraint(name, scope, mapOfPredicates.get(reference), parameters.getTextContent());
        }

        nbGlobalConstraints++;
        String lreference = reference.toLowerCase();
        Element parameters = (Element) constraintElement.getElementsByTagName(InstanceTokens.PARAMETERS).item(0);

        if (lreference.equals(InstanceTokens.getLowerCaseGlobalNameOf(InstanceTokens.ALL_DIFFERENT))) {
            return new PAllDifferent(name, scope);
        }
        if (lreference.equals(InstanceTokens.getLowerCaseGlobalNameOf(InstanceTokens.ELEMENT))) {
            return parseElementConstraint(name, scope, parameters);
        }
        if (lreference.equals(InstanceTokens.getLowerCaseGlobalNameOf(InstanceTokens.WEIGHTED_SUM))) {
            return parseWeightedSumConstraint(name, scope, parameters);
        }
        if (lreference.equals(InstanceTokens.getLowerCaseGlobalNameOf(InstanceTokens.CUMULATIVE))) {
            return parseCumulativeConstraint(name, scope, parameters);
        }

        System.out.println("Problem with the reference " + reference);
        return null;
    }

    private void parseConstraints(Element constraintsElement) {
        mapOfConstraints = new HashMap<String, PConstraint>();
        int nbConstraints = Integer.parseInt(constraintsElement.getAttribute(InstanceTokens.NB_CONSTRAINTS));
        setNbCons(nbConstraints);
        constraints = new ArrayList<PConstraint>();
        if (displayInstance) {
            System.out.print("=> " + nbConstraints + " constraints");
            if (type.equals(InstanceTokens.WCSP)) {
                int maximalCost = Integer.parseInt(constraintsElement.getAttribute(InstanceTokens.MAXIMAL_COST));
                String s = constraintsElement.getAttribute(InstanceTokens.INITIAL_COST);
                int initialCost = s.equals("") ? 0 : Integer.parseInt(s);
                System.out.print(" maximalCost=" + maximalCost + " initialCost=" + initialCost);
            }
            System.out.println();
        }


        NodeList nodeList = constraintsElement.getElementsByTagName(InstanceTokens.CONSTRAINT);
        for (int i = 0; i < nodeList.getLength(); i++) {
            PConstraint constraint = parseConstraint((Element) nodeList.item(i));

            constraint.setReference(constraint_reference); 

            if (nbExtensionConstraints == 0) {
            	constraint.setType("Intension");
                //def = new PDefinition("Intension", mapOfRelations.get(constraint.getReference()));
            } else {
				constraint.setType(tuples_type);
            	//def = new PDefinition(tuples_type, mapOfPredicates.get(constraint.getReference()));
            }
            mapOfConstraints.put(constraint.getName(), constraint);
            if (displayInstance) {
                System.out.println(constraint);
            }
            constraints.add(constraint);
        }

    }

    public void parse(boolean displayInstance) {
        this.displayInstance = displayInstance;
        parsePresentation((Element) document.getDocumentElement().getElementsByTagName(InstanceTokens.PRESENTATION).item(0));
        parseDomains((Element) document.getDocumentElement().getElementsByTagName(InstanceTokens.DOMAINS).item(0));
        parseVariables((Element) document.getDocumentElement().getElementsByTagName(InstanceTokens.VARIABLES).item(0));
        parseRelations((Element) document.getDocumentElement().getElementsByTagName(InstanceTokens.RELATIONS).item(0));
        parseFunctions((Element) document.getDocumentElement().getElementsByTagName(InstanceTokens.FUNCTIONS).item(0));
        parsePredicates((Element) document.getDocumentElement().getElementsByTagName(InstanceTokens.PREDICATES).item(0));
        parseConstraints((Element) document.getDocumentElement().getElementsByTagName(InstanceTokens.CONSTRAINTS).item(0));
    }

	public static String getVersion() {
		return VERSION;
	}

	public int getNbVars() {
		return nbVars;
	}

	public void setNbVars(int nbVars) {
		this.nbVars = nbVars;
	}

	public int getNbCons() {
		return nbCons;
	}

	public void setNbCons(int nbCons) {
		this.nbCons = nbCons;
	}
}
    