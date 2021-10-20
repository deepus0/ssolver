package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuGrid;

public interface SudokuRule {

    boolean apply(SudokuGrid grid);
}
