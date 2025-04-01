package com.example.demo.Generator;

/**
 * Encodes a (row, column, digit) tuple into a unique variable index.
 * Works for any grid size.
 */
public class SudokuEncoderImpl implements SudokuEncoder {
  private final int size;

  public SudokuEncoderImpl(int size) {
    this.size = size;
  }

  /**
   * Encodes a cell (row, column, number) into a single variable number.
   * Variable indices range from 1 to size³.
   *
   * @param row   1-based row
   * @param col   1-based column
   * @param digit 1-based digit (1–size)
   * @return variable index (1–size³)
   */
  @Override
  public int encode(int row, int col, int digit) {
    return (row - 1) * size * size + (col - 1) * size + digit;
  }

  /**
   * Decodes a variable index into a cell (row, column, number).
   *
   * @param cell variable index (1–size³)
   * @return (row, column, number)
   */
  @Override
  public int[] decode(int cell) {
    cell--; // zero-based
    int digit = cell % size + 1;
    int col = (cell / size) % size + 1;
    int row = (cell / (size * size)) + 1;
    return new int[]{row, col, digit};
  }
}
