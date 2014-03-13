package cspsolver.instance.tools.solver;

import java.util.Comparator;

import cspsolver.instance.tools.InstanceParser;

class lexicomparator implements Comparator<String> {

    InstanceParser outer;

    lexicomparator(InstanceParser outer) {
        this.outer = outer;
    }

    public int compare(String strA, String strB) {
        return strA.compareToIgnoreCase(strB);
    }
}
