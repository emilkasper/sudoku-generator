package Setup;

import Config.SudokuConfigClassic;
import java.util.List;
import java.util.Random;

/**
 * Utility class to inject random valid hints (givens) into a Sudoku CNF clause list.
 */
public class SudokuRandomizer {

  private static final int MAX_ATTEMPTS = 1000;

  /**
   * Adds a specified number of random, non-conflicting hints to a clause list.
   *
   * @param clauses  the list of CNF clauses for the Sudoku instance
   * @param numHints the number of hints (unit clauses) to inject
   */
  public static void addRandomHints(final List<int[]> clauses, int numHints) {
    Random rand = new Random();
    SudokuConfigClassic config = new SudokuConfigClassic();
    SudokuHintTracker tracker = new SudokuHintTracker(config);
    SudokuEncoderImpl encoder = new SudokuEncoderImpl(config.getSize());

    int size = config.getSize();
    int attempts = 0;

    while (numHints > 0 && attempts < MAX_ATTEMPTS) {
      int row = rand.nextInt(size) + 1;
      int col = rand.nextInt(size) + 1;
      int digit = rand.nextInt(size) + 1;

      if (tracker.isValid(row, col, digit)) {
        clauses.add(new int[]{encoder.encode(row, col, digit)});
        tracker.apply(row, col, digit);
        numHints--;
      }

      attempts++;
    }
  }
}
