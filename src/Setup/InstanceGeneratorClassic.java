package Setup;

import Config.SudokuConfig;
import Config.SudokuConfigClassic;
import SATSolver.SATSolver;
import SATSolver.SATSolverDPLL;
import SATSolver.SATSolverResult;

import java.util.List;

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
