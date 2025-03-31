package Output;

public class SudokuPrinter {

  /**
   * Print a 9x9x1 Sudoku grid to the console.
   */
  public static void print(int[][][] sudoku) {
    for (int row = 0; row < 9; row++) {
      if (row % 3 == 0 && row != 0) {
        System.out.println("------+-------+------");
      }
      for (int col = 0; col < 9; col++) {
        if (col % 3 == 0 && col != 0) System.out.print("| ");
        int value = sudoku[row][col][0];
        System.out.print((value == 0 ? "." : value) + " ");
      }
      System.out.println();
    }
  }
}
