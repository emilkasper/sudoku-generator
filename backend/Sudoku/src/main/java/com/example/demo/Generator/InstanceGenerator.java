package com.example.demo.Generator;

public interface InstanceGenerator {

  /*
   * Generates a Sudoku instance.
   *
   * @return a Sudoku instance
   */
  int[][][] generateInstance();

  /**
   * Removes a number of values from a fully solved Sudoku grid to create a puzzle.
   *
   * @param solvedGrid the fully solved grid [size][size][1]
   * @param numToRemove number of digits to remove (create zeros)
   * @return a partially filled Sudoku grid
   */
  int[][][] generatePuzzleFromSolved(int[][][] solvedGrid, int numToRemove);

  /**
   * Returns a tuple of a puzzle and its solved instance.
   *
   * @return a puzzle-solution pair
   */
  int[][][][] generatePuzzlePair();
}
