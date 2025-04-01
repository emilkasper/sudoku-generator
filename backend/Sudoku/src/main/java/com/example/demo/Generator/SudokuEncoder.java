package com.example.demo.Generator;

public interface SudokuEncoder {
  /**
   * Encode a cell in a Sudoku grid.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @param digit the digit of the cell
   * @return the encoded cell
   */
  int encode(int row, int col, int digit);

  /**
   * Decode a cell in a Sudoku grid.
   *
   * @param cell the encoded cell
   * @return the decoded cell
   */
  int[] decode(int cell);
}
