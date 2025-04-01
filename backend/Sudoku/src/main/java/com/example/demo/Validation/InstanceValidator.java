package com.example.demo.Validation;

public interface InstanceValidator {

  /*
    * This method validates the instance.
    *
    * @param instance The instance to validate.
   */
  void validateInstance(int[][][] instance) throws IllegalArgumentException;

}
