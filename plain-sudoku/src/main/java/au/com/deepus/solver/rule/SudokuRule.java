package au.com.deepus.solver.rule;

import au.com.deepus.models.grid.SudokuGrid;

public interface SudokuRule {

    boolean apply(SudokuGrid grid);
}
