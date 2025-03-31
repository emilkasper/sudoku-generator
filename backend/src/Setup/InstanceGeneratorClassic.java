package Setup;

import Config.SudokuConfig;
import Config.SudokuConfigClassic;
import SATSolver.SATSolver;
import SATSolver.SATSolverDPLL;
import SATSolver.SATSolverResult;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Generates a complete classic Sudoku instance using SAT solving.
 */
public class InstanceGeneratorClassic implements InstanceGenerator {

  private final SudokuConfig config;
  private final int maxNumHints = 20;

  public InstanceGeneratorClassic() {
    this.config = new SudokuConfigClassic();
  }

  /**
   * Generates a classic Sudoku instance.
   *
   * @return a classic Sudoku instance
   */
  @Override
  public int[][][] generateInstance() {
    int size = config.getSize();
    SudokuEncoder encoder = new SudokuEncoderImpl(size);
    SudokuClauseGenerator clauseGenerator = new SudokuClauseGeneratorClassic(size, encoder);
    List<int[]> clauses = clauseGenerator.generateClauses();

    SudokuRandomizer.addRandomHints(clauses, maxNumHints);

    SATSolver solver = new SATSolverDPLL();
    SATSolverResult result = solver.solve(clauses, size * size * size);

    if (result.isSatisfiable()) {
      return decodeSudoku(result.getAssignment(), size);
    }
    return new int[0][][]; // unsatisfiable
  }

  /**
   * Generates a classic Sudoku puzzle by removing cells from a solved grid.
   *
   * @param solvedGrid the solved grid
   * @param numToRemove the number of cells to remove
   * @return a classic Sudoku puzzle
   */
  @Override
  public int[][][] generatePuzzleFromSolved(int[][][] solvedGrid, int numToRemove) {
    int size = config.getSize();
    int totalCells = size * size;

    if (numToRemove < 0 || numToRemove > totalCells) {
      throw new IllegalArgumentException("Invalid number of cells to remove: " + numToRemove);
    }

    // Deep copy the solved grid to not modify the original
    int[][][] puzzle = new int[size][size][1];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        puzzle[i][j][0] = solvedGrid[i][j][0];
      }
    }

    // Randomly remove cells
    Random rand = new Random();
    Set<String> removed = new HashSet<>();

    while (removed.size() < numToRemove) {
      int row = rand.nextInt(size);
      int col = rand.nextInt(size);
      String key = row + "," + col;
      if (puzzle[row][col][0] != 0 && !removed.contains(key)) {
        puzzle[row][col][0] = 0;
        removed.add(key);
      }
    }

    return puzzle;
  }


  private int[][][] decodeSudoku(int[] assignment, int size) {
    int[][][] sudoku = new int[size][size][1];
    for (int i = 1; i < assignment.length; i++) {
      if (assignment[i] == 1) {
        int digit = (i - 1) % size + 1;
        int col = ((i - 1) / size) % size + 1;
        int row = (i - 1) / (size * size) + 1;
        sudoku[row - 1][col - 1][0] = digit;
      }
    }
    return sudoku;
  }
}
