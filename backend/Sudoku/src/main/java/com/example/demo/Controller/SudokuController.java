package com.example.demo.controller;

import com.example.demo.Generator.InstanceGeneratorClassic;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SudokuController {

  @GetMapping("/generate")
  public int[][][][] generate(@RequestParam(defaultValue = "Medium") String difficulty) {
    int numRemove = switch (difficulty.toLowerCase()) {
      case "easy" -> 40;
      case "hard" -> 60;
      default -> 50;
    };

    InstanceGeneratorClassic generator = new InstanceGeneratorClassic(numRemove);
    return generator.generatePuzzlePair();
  }


}
