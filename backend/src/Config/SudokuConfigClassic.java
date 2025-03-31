package Config;

/**
 * Standard 9x9 Sudoku configuration with 3x3 boxes.
 */
public class SudokuConfigClassic implements SudokuConfig {

  @Override
  public int getSize() {
    return 9;
  }

  @Override
  public int getBoxRows() {
    return 3;
  }

  @Override
  public int getBoxCols() {
    return 3;
  }
}
