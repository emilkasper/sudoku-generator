# DPLL Sudoku Solver & Generator

A full-stack web application that **generates** and **solves** classic 9x9 Sudoku puzzles using a custom SAT-based backend and an interactive frontend.

---

## Features

- Generate Sudoku puzzles at three difficulty levels: Easy, Medium, Hard
- Solve puzzles with one click or try them manually
- Built-in solution verification
- Custom loader with cancellation option
- Interactive frontend (HTML/CSS/JavaScript)
- Backend powered by a SAT solver (Java + Spring Boot)

---

## How it Works

Sudoku puzzles are encoded as SAT problems using CNF clauses.  
The backend solves the CNF using a **custom implementation of the DPLL algorithm** with unit propagation:
729 variables are used to represent all possible combinations, e.g. x_2_3_5 describes that the cell at row 2, column 3, contains the digit 5. 
Each sudoku rule is then translated into clauses: 
  - Each cell contains at least one digit: (x_1_1_1 ∨ x_1_1_2 ∨ ... ∨ x_1_1_9)
  - Each cell contains at most one digit: (¬x_1_1_1 ∨ ¬x_1_1_2), (¬x_1_1_1 ∨ ¬x_1_1_3), ..., (¬x_1_1_8 ∨ ¬x_1_1_9)
  - Each digit appears once per row: (¬x_1_1_5 ∨ ¬x_1_2_5), (¬x_1_1_5 ∨ ¬x_1_3_5), ...
  - Each digit appears once per column: (¬x_1_1_3 ∨ ¬x_2_1_3), (¬x_1_1_3 ∨ ¬x_3_1_3), ...
  - Each digit appears once per 3×3 block: (¬x_1_1_4 ∨ ¬x_1_2_4), (¬x_1_1_4 ∨ ¬x_2_1_4), ...
Although DPLL is **not the most efficient SAT solver available today**, it works well for this small project.

---

## Tech Stack

- **Frontend:** HTML, CSS, JavaScript
- **Backend:** Java 21, Spring Boot, Maven
- **Solver Logic:** Custom DPLL implementation

---

## Setup

1. Clone this repo
2. Navigate to the backend folder and run:
   ```bash
   mvn spring-boot:run
