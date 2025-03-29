package SATSolver;

import java.util.*;

public class SATSolverDPLL implements SATSolver {

  /**
   * Solve a formula given as a list of CNF clauses.
   * Each clause is an array of integers (positive or negative literals).
   * Performs the DPLL algorithm with unit propagation to determine satisfiability.
   *
   * @param clauses       CNF clauses
   * @param numVariables  number of distinct variables (max variable index)
   * @return result object with satisfiability and assignment (if satisfiable)
   */
  @Override
  public SATSolverResult solve(List<int[]> clauses, int numVariables) {
    int[] assignment = new int[numVariables + 1]; // 1-indexed
    List<int[]> clausesCopy = deepCopy(clauses);
    boolean success = dpll(clausesCopy, assignment);
    return new SATSolverResult(success, assignment);
  }

  private boolean dpll(List<int[]> clauses, int[] assignment) {
    clauses = unitPropagation(clauses, assignment);
    if (clauses == null) return false;
    if (clauses.isEmpty()) return true;

    int variable = chooseVariable(clauses, assignment);
    if (variable == -1) return true;

    int[] assignmentCopy = Arrays.copyOf(assignment, assignment.length);
    assignmentCopy[variable] = 1;
    if (dpll(assign(clauses, variable, true), assignmentCopy)) {
      System.arraycopy(assignmentCopy, 0, assignment, 0, assignment.length);
      return true;
    }

    assignmentCopy = Arrays.copyOf(assignment, assignment.length);
    assignmentCopy[variable] = -1;
    if (dpll(assign(clauses, variable, false), assignmentCopy)) {
      System.arraycopy(assignmentCopy, 0, assignment, 0, assignment.length);
      return true;
    }

    return false;
  }

  private List<int[]> unitPropagation(List<int[]> clauses, int[] assignment) {
    List<int[]> workingClauses = new ArrayList<>(clauses);
    boolean changed;
    do {
      changed = false;
      Iterator<int[]> it = workingClauses.iterator();
      while (it.hasNext()) {
        int[] clause = it.next();
        int unassignedCount = 0;
        int lastUnassigned = 0;
        boolean satisfied = false;

        for (int literal : clause) {
          int var = Math.abs(literal);
          if (assignment[var] == (literal > 0 ? 1 : -1)) {
            satisfied = true;
            break;
          } else if (assignment[var] == 0) {
            unassignedCount++;
            lastUnassigned = literal;
          }
        }

        if (satisfied) {
          it.remove();
        } else if (unassignedCount == 0) {
          return null; // conflict
        } else if (unassignedCount == 1) {
          int var = Math.abs(lastUnassigned);
          assignment[var] = lastUnassigned > 0 ? 1 : -1;
          it.remove();
          workingClauses = assign(workingClauses, var, assignment[var] == 1);
          changed = true;
          break;
        }
      }
    } while (changed);

    return workingClauses;
  }

  private int chooseVariable(List<int[]> clauses, int[] assignment) {
    for (int[] clause : clauses) {
      for (int literal : clause) {
        int var = Math.abs(literal);
        if (assignment[var] == 0) return var;
      }
    }
    return -1;
  }

  private List<int[]> assign(List<int[]> clauses, int variable, boolean value) {
    List<int[]> newClauses = new ArrayList<>();
    int trueLiteral = value ? variable : -variable;
    int falseLiteral = value ? -variable : variable;

    for (int[] clause : clauses) {
      boolean satisfied = false;
      List<Integer> newClause = new ArrayList<>();
      for (int lit : clause) {
        if (lit == trueLiteral) {
          satisfied = true;
          break;
        }
        if (lit != falseLiteral) {
          newClause.add(lit);
        }
      }
      if (!satisfied) {
        newClauses.add(newClause.stream().mapToInt(i -> i).toArray());
      }
    }

    return newClauses;
  }

  private List<int[]> deepCopy(List<int[]> clauses) {
    List<int[]> copy = new ArrayList<>();
    for (int[] clause : clauses) {
      copy.add(Arrays.copyOf(clause, clause.length));
    }
    return copy;
  }
}
