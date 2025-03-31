package SATSolver;

import java.util.List;

public interface SATSolver {
  /**
   * Solve a formula given as a list of CNF clauses.
   * Each clause is an array of integers (positive or negative literals).
   *
   * @param clauses       CNF clauses
   * @param numVariables  number of distinct variables (max variable index)
   * @return result object with satisfiability and assignment (if satisfiable)
   */
  SATSolverResult solve(List<int[]> clauses, int numVariables);
}
