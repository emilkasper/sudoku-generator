function createGrid() {
    const board = document.getElementById("sudoku-board");
    board.innerHTML = "";
  
    for (let boxRow = 0; boxRow < 3; boxRow++) {
      for (let boxCol = 0; boxCol < 3; boxCol++) {
        const gridBox = document.createElement("div");
        gridBox.className = "grid-box";
  
        for (let cellRow = 0; cellRow < 3; cellRow++) {
          for (let cellCol = 0; cellCol < 3; cellCol++) {
            const globalRow = boxRow * 3 + cellRow;
            const globalCol = boxCol * 3 + cellCol;
  
            const input = document.createElement("input");
            input.type = "text";
            input.maxLength = 1;
            input.className = "grid-cell";
            input.id = `cell-${globalRow}-${globalCol}`;
            input.oninput = function () {
              this.value = this.value.replace(/[^1-9]/g, '');
            };
  
            gridBox.appendChild(input);
          }
        }
  
        board.appendChild(gridBox);
      }
    }
  }
  
  
  function clearPuzzle() {
    for (let row = 0; row < 9; row++) {
      for (let col = 0; col < 9; col++) {
        const cell = document.getElementById(`cell-${row}-${col}`);
        cell.value = "";
        cell.classList.remove("prefilled");
      }
    }
  }
  
  function fillPuzzle(grid) {
    for (let row = 0; row < 9; row++) {
      for (let col = 0; col < 9; col++) {
        const value = grid[row][col][0];
        const cell = document.getElementById(`cell-${row}-${col}`);
        if (value !== 0) {
          cell.value = value;
          cell.classList.add("prefilled");
        } else {
          cell.value = "";
          cell.classList.remove("prefilled");
        }
      }
    }
  }
  
  // Replace with actual backend endpoint when ready
  function generatePuzzle() {
    fetch("http://localhost:8080/api/generate") 
      .then((res) => res.json())
      .then((grid) => fillPuzzle(grid))
      .catch((err) => console.error("Failed to fetch puzzle:", err));
  }
  
  function solvePuzzle() {
    alert("Solver not implemented yet");
  }
  
  window.onload = createGrid;
  