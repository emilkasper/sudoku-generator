package com.example.demo.Config;

/**
 * Defines the structural configuration for a Sudoku grid.
 * Supports flexible grid and box sizes (e.g. 9x9, 6x6, 16x16).
 */
public interface SudokuConfig {
  int getSize();       // Total grid size (e.g., 9 for 9x9)
  int getBoxRows();    // Number of rows per box (e.g., 3 for classic)
  int getBoxCols();    // Number of columns per box (e.g., 3 for classic)
}
