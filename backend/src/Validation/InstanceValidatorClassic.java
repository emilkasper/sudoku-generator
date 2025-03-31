package Validation;

import Config.SudokuConfig;
import Config.SudokuConfigClassic;

public class InstanceValidatorClassic implements InstanceValidator {

  private static final int SIZE;
  private static final int BOX_SIZE;

  static {
    SudokuConfig config = new SudokuConfigClassic();
    SIZE = config.getSize();
    BOX_SIZE = config.getBoxRows();
  }

  /*
    * This method validates the instance.
    *
    * @param instance The instance to validate.
    * @throws IllegalArgumentException if the instance is invalid.
   */
  @Override
  public void validateInstance(int[][][] instance) throws IllegalArgumentException {
    validateRows(instance);
    validateColumns(instance);
    validateBoxes(instance);
  }

  /*
    * This method validates the rows of the instance.
    *
    * @param instance The instance to validate.
    * @throws IllegalArgumentException if the instance has an invalid row.
   */
  private void validateRows(int[][][] instance) throws IllegalArgumentException {
    for (int row = 0; row < SIZE; row++) {
      boolean[] found = new boolean[SIZE];
      for (int col = 0; col < SIZE; col++) {
        int num = instance[row][col][0];
        if (num < 1 || num > SIZE || found[num - 1]) {
          throw new IllegalArgumentException("Invalid row " + (row + 1) + ".");
        }
        found[num - 1] = true;
      }
    }
  }

  /*
    * This method validates the columns of the instance.
    *
    * @param instance The instance to validate.
    * @throws IllegalArgumentException if the instance has an invalid column.
   */
  private void validateColumns(int[][][] instance) throws IllegalArgumentException{
    for (int col = 0; col < SIZE; col++) {
      boolean[] found = new boolean[SIZE];
      for (int row = 0; row < SIZE; row++) {
        int num = instance[row][col][0];
        if (num < 1 || num > SIZE || found[num - 1]) {
          throw new IllegalArgumentException("Invalid column " + (col + 1) + ".");
        }
        found[num - 1] = true;
      }
    }
  }

  /*
    * This method validates the boxes of the instance.
    *
    * @param instance The instance to validate.
    * @throws IllegalArgumentException if the instance has an invalid box.
   */
  private void validateBoxes(int[][][] instance) throws IllegalArgumentException {
    for (int startRow = 0; startRow < SIZE; startRow += BOX_SIZE) {
      for (int startCol = 0; startCol < SIZE; startCol += BOX_SIZE) {
        boolean[] found = new boolean[SIZE];
        for (int row = startRow; row < startRow + BOX_SIZE; row++) {
          for (int col = startCol; col < startCol + BOX_SIZE; col++) {
            int num = instance[row][col][0];
            if (num < 1 || num > SIZE || found[num - 1]) {
              throw new IllegalArgumentException("Invalid box at (" + (row + 1) + ", " + (col + 1) + ").");
            }
            found[num - 1] = true;
          }
        }
      }
    }
  }
}
