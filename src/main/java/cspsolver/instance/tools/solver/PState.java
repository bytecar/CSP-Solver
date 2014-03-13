package cspsolver.instance.tools.solver;

public class PState {

private String search;
private String value_order_heuristic;
private String var_static_dynamic;
private String cspname;
private String solutions;

public double cpu_time;
public int CC;
public int nv;
public int bt;

    PState(String searchName, String heuristics,String cspName) {
        
        this.search=searchName;
        this.value_order_heuristic=heuristics;
        //this.var_static_dynamic=string1;
        this.cspname=cspName;
       // this.solutions=string3;
    }


    public void PState(String search, String voh, String dynamicity,String name)    {
        this.search=search;
        this.value_order_heuristic=voh;
        this.var_static_dynamic=dynamicity;
        this.cspname=name;
    };

    public String getSearch()  {
      return search;  
    };
    
    public String getvoh()  {
      return value_order_heuristic;  
    };
    public String getDynamicity()  {
      return var_static_dynamic;  
    };

    public void OutString()   {
        System.out.println("\nCSP(-f): "+cspname+"\nSearch(-a): "+search+"\nValue Ordering(-u): "+value_order_heuristic);//+"\nDynamicity: "+var_static_dynamic);
        System.out.println();
        System.out.println("Constraint-Checks(CC): "+CC+"\nNodes-Visited(nv): "+nv+"\nBacktracks(bt): "+bt);
    }

    public String getSolutions() {
        return solutions;
    }

}
