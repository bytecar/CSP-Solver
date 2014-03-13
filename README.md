CSP-Solver
==========

BT, CBJ, FCCBJ

usage: CSP-Solver [-a <arg>] [-f <arg>] [-h] [-p <arg>] [-s <arg>] [-u <arg>]

 -a,--search <arg>           Search: BT, CBJ, FC, FCCBJ
 -f,--inputfile <arg>        Path to file in XCSP 2.0 format
 -h,--help                   Help for using Solver
 -p,--printsolutions <arg>   Optional: Append "-p p" to print solutions,
                             "-p n" not to print solutions
 -s,--solution <arg>         Solutions: "1" for single solution, "all" for
                             all solutions
 -u,--heuristics <arg>       Variable Ordering Heuristic:
                             LX(lexicographic), LD(least domain), DEG(max
                             degree), DD(domain degree ratio), W(Minimum
                             Width)
