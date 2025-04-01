package com.example.demo.Output;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Exports a Sudoku grid to a .txt file in readable format.
 */
public class SudokuFileExporter {

  /**
   * Exports the Sudoku grid to a text file.
   *
   * @param sudoku   The 9x9x1 Sudoku grid to export
   * @param filename The path to the output file (e.g., "sudoku.txt")
   * @throws IOException if file writing fails
   */
  public static void exportToFile(int[][][] sudoku, String filename) throws IOException {
    try (FileWriter writer = new FileWriter(filename)) {
      for (int row = 0; row < 9; row++) {
        if (row % 3 == 0 && row != 0) {
          writer.write("------+-------+------\n");
        }
        for (int col = 0; col < 9; col++) {
          if (col % 3 == 0 && col != 0) writer.write("| ");
          int value = sudoku[row][col][0];
          writer.write((value == 0 ? "." : value) + " ");
        }
        writer.write("\n");
      }
    }
  }

}
