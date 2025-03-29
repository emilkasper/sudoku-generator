  import Output.SudokuFileExporter;
  import Output.SudokuPrinter;
  import Setup.InstanceGeneratorClassic;
  import Validation.InstanceValidator;
  import Validation.InstanceValidatorClassic;

  import java.io.IOException;
  import java.util.logging.Logger;

  public class Main {

    public static void main(String[] args) {
      Logger logger = Logger.getLogger(Main.class.getName());

      logger.info("Generating Sudoku instance...");
      InstanceGeneratorClassic generator = new InstanceGeneratorClassic();
      int[][][] sudoku = generator.generateInstance();

      logger.info("Validating Sudoku instance...");
      InstanceValidator validator = new InstanceValidatorClassic();
      validator.validateInstance(sudoku);

      logger.info("Printing Sudoku instance...");
      try {
        SudokuFileExporter.exportToFile(sudoku, "sudoku_output.txt");
        logger.info("Sudoku exported to sudoku_output.txt");
      } catch (IOException e) {
        logger.severe("Failed to write Sudoku to file: " + e.getMessage());
      }

      // Alternatively, print the Sudoku instance to the console
      // SudokuPrinter.print(sudoku);
    }
  }