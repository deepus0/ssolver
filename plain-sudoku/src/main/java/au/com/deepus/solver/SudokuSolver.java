package au.com.deepus.solver;

import au.com.deepus.models.grid.SudokuGrid;

public interface SudokuSolver {

    SudokuGrid solve(SudokuGrid sudoku);
}
