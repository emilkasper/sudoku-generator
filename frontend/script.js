let controller = null;
let solutionGrid = null;

window.onload = function () {
  createGrid();

  // Disable Solve button initially
  const solveBtn = document.querySelector("button[onclick='solvePuzzle()']");
  if (solveBtn) solveBtn.disabled = true;
};

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

function showLoader() {
  document.getElementById("loading-overlay").style.display = "flex";
}

function hideLoader() {
  document.getElementById("loading-overlay").style.display = "none";
}

function cancelGeneration() {
  if (controller) {
    controller.abort();
    controller = null;
    hideLoader();
    console.log("Generation cancelled.");
  }
}

function generatePuzzle() {
  const difficulty = document.getElementById("difficulty").value;
  controller = new AbortController();
  showLoader();

  fetch(`http://localhost:8080/api/generate?difficulty=${difficulty}`, {
    signal: controller.signal,
  })
    .then((res) => res.json())
    .then((data) => {
      const [puzzle, solution] = data;
      solutionGrid = solution;
      fillPuzzle(puzzle);

      const solveBtn = document.querySelector("button[onclick='solvePuzzle()']");
      if (solveBtn) solveBtn.disabled = false;
    })
    .catch((err) => {
      if (err.name === "AbortError") {
        console.log("Fetch aborted");
      } else {
        console.error("Failed to fetch puzzle:", err);
        alert("Puzzle generation failed.");
      }
    })
    .finally(() => {
      hideLoader();
      controller = null;
    });
}


function solvePuzzle() {
  if (!solutionGrid) {
    alert("Please generate a puzzle first!");
    return;
  }

  for (let row = 0; row < 9; row++) {
    for (let col = 0; col < 9; col++) {
      const cell = document.getElementById(`cell-${row}-${col}`);
      const correctValue = solutionGrid[row][col][0];
      const userValue = parseInt(cell.value);

      cell.classList.remove("incorrect");
      cell.classList.remove("solution-hint");

      if (cell.classList.contains("prefilled")) {
        continue; // Skip original clues
      }

      if (!cell.value) {
        cell.value = correctValue;
        cell.classList.add("solution-hint");
      } else if (userValue !== correctValue) {
        cell.classList.add("incorrect");
      }
    }
  }
}

function clearPuzzle() {
  for (let row = 0; row < 9; row++) {
    for (let col = 0; col < 9; col++) {
      const cell = document.getElementById(`cell-${row}-${col}`);
      cell.value = "";
      cell.classList.remove("prefilled");
      cell.classList.remove("incorrect");
    }
  }

  const solveBtn = document.querySelector("button[onclick='solvePuzzle()']");
  if (solveBtn) solveBtn.disabled = true;
}