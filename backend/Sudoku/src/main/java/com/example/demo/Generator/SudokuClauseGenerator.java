package com.example.demo.Generator;

import java.util.List;

/**
 * Interface for generating CNF clauses that represent Sudoku constraints.
 */
public interface SudokuClauseGenerator {

  /**
   * Generates CNF clauses that represent the rules of a specific Sudoku variant.
   *
   * @return A list of clauses, where each clause is an array of integers (literals).
   */
  List<int[]> generateClauses();
}
