package com.example.demo.Solver;

public class SATSolverResult {
  private final boolean satisfiable;
  private final int[] assignment; // Index 1-based: assignment[i] ∈ {1, -1, 0}

  public SATSolverResult(boolean satisfiable, int[] assignment) {
    this.satisfiable = satisfiable;
    this.assignment = assignment;
  }

  public boolean isSatisfiable() {
    return satisfiable;
  }

  public int[] getAssignment() {
    return assignment;
  }
}
