package cspsolver.instance.tools.solver;

import java.util.Comparator;

class LexicographicComparator implements Comparator<String> {
    public int compare(String strA, String strB) {
        return strA.compareToIgnoreCase(strB);
    }
}
