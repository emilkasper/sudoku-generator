package Validation;

public interface InstanceValidator {

  /*
   * This method validates the format of the instance.
   *
   * @param instance The instance to validate.
   */
  void validateFormat(int[][][] instance) throws IllegalArgumentException;

  /*
    * This method validates the instance.
    *
    * @param instance The instance to validate.
   */
  void validateInstance(int[][][] instance) throws IllegalArgumentException;

}
