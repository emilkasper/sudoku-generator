package com.example.demo.Generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates CNF clauses for classic Sudoku rules using a SudokuEncoder.
 */
public class SudokuClauseGeneratorClassic implements SudokuClauseGenerator {

  private final int size;
  private final SudokuEncoder encoder;

  public SudokuClauseGeneratorClassic(int size, SudokuEncoder encoder) {
    this.size = size;
    this.encoder = encoder;
  }

  /**
   * Generates CNF clauses that represent the rules of classic Sudoku.
   *
   * @return A list of clauses, where each clause is an array of integers (literals).
   */
  @Override
  public List<int[]> generateClauses() {
    List<int[]> clauses = new ArrayList<>();
    clauses.addAll(generateCellAtLeastOneDigitClauses());
    clauses.addAll(generateCellAtMostOneDigitClauses());
    clauses.addAll(generateRowUniquenessClauses());
    clauses.addAll(generateColumnUniquenessClauses());
    clauses.addAll(generateBoxUniquenessClauses());
    return clauses;
  }

  private List<int[]> generateCellAtLeastOneDigitClauses() {
    List<int[]> clauses = new ArrayList<>();
    for (int row = 1; row <= size; row++) {
      for (int col = 1; col <= size; col++) {
        int[] clause = new int[size];
        for (int num = 1; num <= size; num++) {
          clause[num - 1] = encoder.encode(row, col, num);
        }
        clauses.add(clause);
      }
    }
    return clauses;
  }

  private List<int[]> generateCellAtMostOneDigitClauses() {
    List<int[]> clauses = new ArrayList<>();
    for (int row = 1; row <= size; row++) {
      for (int col = 1; col <= size; col++) {
        for (int d1 = 1; d1 <= size; d1++) {
          for (int d2 = d1 + 1; d2 <= size; d2++) {
            clauses.add(new int[]{
                -encoder.encode(row, col, d1),
                -encoder.encode(row, col, d2)
            });
          }
        }
      }
    }
    return clauses;
  }

  private List<int[]> generateRowUniquenessClauses() {
    List<int[]> clauses = new ArrayList<>();
    for (int row = 1; row <= size; row++) {
      for (int num = 1; num <= size; num++) {
        for (int col1 = 1; col1 <= size; col1++) {
          for (int col2 = col1 + 1; col2 <= size; col2++) {
            clauses.add(new int[]{
                -encoder.encode(row, col1, num),
                -encoder.encode(row, col2, num)
            });
          }
        }
      }
    }
    return clauses;
  }

  private List<int[]> generateColumnUniquenessClauses() {
    List<int[]> clauses = new ArrayList<>();
    for (int col = 1; col <= size; col++) {
      for (int num = 1; num <= size; num++) {
        for (int row1 = 1; row1 <= size; row1++) {
          for (int row2 = row1 + 1; row2 <= size; row2++) {
            clauses.add(new int[]{
                -encoder.encode(row1, col, num),
                -encoder.encode(row2, col, num)
            });
          }
        }
      }
    }
    return clauses;
  }

  private List<int[]> generateBoxUniquenessClauses() {
    List<int[]> clauses = new ArrayList<>();
    int boxSize = (int) Math.sqrt(size); // assumes square boxes

    for (int blockRow = 0; blockRow < boxSize; blockRow++) {
      for (int blockCol = 0; blockCol < boxSize; blockCol++) {
        for (int num = 1; num <= size; num++) {
          List<Integer> boxCells = new ArrayList<>();
          for (int r = 1; r <= boxSize; r++) {
            for (int c = 1; c <= boxSize; c++) {
              int row = blockRow * boxSize + r;
              int col = blockCol * boxSize + c;
              boxCells.add(encoder.encode(row, col, num));
            }
          }
          for (int i = 0; i < boxCells.size(); i++) {
            for (int j = i + 1; j < boxCells.size(); j++) {
              clauses.add(new int[]{ -boxCells.get(i), -boxCells.get(j) });
            }
          }
        }
      }
    }
    return clauses;
  }
}
