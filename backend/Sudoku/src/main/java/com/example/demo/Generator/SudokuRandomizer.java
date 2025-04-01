package com.example.demo.Generator;

import com.example.demo.Config.SudokuConfigClassic;
import java.util.List;
import java.util.Random;

/**
 * Utility class to inject random valid hints (givens) into a Sudoku CNF clause list.
 */
public class SudokuRandomizer {

  private static final int MAX_ATTEMPTS = 1000;
  private static final int NUMBER_OF_HINTS = 15;

  /**
   * Adds a specified number of random, non-conflicting hints to a clause list.
   *
   * @param clauses  the list of CNF clauses for the Sudoku instance
   */
  public static void addRandomHints(final List<int[]> clauses) {
    Random rand = new Random();
    SudokuConfigClassic config = new SudokuConfigClassic();
    SudokuHintTracker tracker = new SudokuHintTracker(config);
    SudokuEncoderImpl encoder = new SudokuEncoderImpl(config.getSize());

    int size = config.getSize();
    int attempts = 0;

    int remaining = NUMBER_OF_HINTS;
    while (remaining > 0 && attempts < MAX_ATTEMPTS) {
      int row = rand.nextInt(size) + 1;
      int col = rand.nextInt(size) + 1;
      int digit = rand.nextInt(size) + 1;

      if (tracker.isValid(row, col, digit)) {
        clauses.add(new int[]{encoder.encode(row, col, digit)});
        tracker.apply(row, col, digit);
        remaining--;
      }

      attempts++;
    }
  }
}
