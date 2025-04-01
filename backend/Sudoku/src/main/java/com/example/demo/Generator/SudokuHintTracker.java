package com.example.demo.Generator;

import com.example.demo.Config.SudokuConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Tracks cell, row, column, and box usage for Sudoku hint generation.
 * Supports any rectangular box size (e.g., 3x3, 2x3).
 */
public class SudokuHintTracker {

  private final SudokuConfig config;

  private final boolean[][] rowUsed; // rowUsed[row][digit]
  private final boolean[][] colUsed; // colUsed[col][digit]
  private final boolean[][] boxUsed; // boxUsed[box][digit]
  private final Set<String> usedCells = new HashSet<>(); // describes the cells that have been used

  /**
   * Constructs a hint tracker for a given Sudoku configuration.
   *
   * @param config The Sudoku configuration
   */
  public SudokuHintTracker(SudokuConfig config) {
    this.config = config;

    int size = config.getSize();
    this.rowUsed = new boolean[size][size + 1];
    this.colUsed = new boolean[size][size + 1];
    this.boxUsed = new boolean[size][size + 1];
  }

  /**
   * Checks if a digit placement is valid.
   * Returns true if the cell is not used, and the digit is not used in the row, column, or box.
   *
   * @param row   Row index (1-based)
   * @param col   Column index (1-based)
   * @param digit Digit to place (1-based)
   * @return      True if the placement is valid
   */
  public boolean isValid(int row, int col, int digit) {
    int box = getBoxIndex(row, col);
    return !usedCells.contains(cellKey(row, col))
        && !rowUsed[row - 1][digit]
        && !colUsed[col - 1][digit]
        && !boxUsed[box][digit];
  }

  /**
   * Applies a digit placement to the tracker.
   * Marks the row, column, and box as used.
   *
   * @param row   Row index (1-based)
   * @param col   Column index (1-based)
   * @param digit Digit to place (1-based)
   */
  public void apply(int row, int col, int digit) {
    int box = getBoxIndex(row, col);
    rowUsed[row - 1][digit] = true;
    colUsed[col - 1][digit] = true;
    boxUsed[box][digit] = true;
    usedCells.add(cellKey(row, col));
  }

  private int getBoxIndex(int row, int col) {
    int boxRow = (row - 1) / config.getBoxRows();
    int boxCol = (col - 1) / config.getBoxCols();
    return boxRow * (config.getSize() / config.getBoxCols()) + boxCol;
  }

  private String cellKey(int row, int col) {
    return row + "," + col;
  }
}
