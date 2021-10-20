package au.com.deepus.solver;

import au.com.deepus.models.SudokuGrid;
import au.com.deepus.solver.rule.HiddenSingleRule;
import au.com.deepus.solver.rule.NakedPairRule;
import au.com.deepus.solver.rule.NakedSingleRule;
import au.com.deepus.solver.rule.PointingPairRule;
import au.com.deepus.solver.rule.RemovePossibilitiesRule;
import au.com.deepus.solver.rule.SudokuRule;

public class SudokuSolverMain implements SudokuSolver {

    private final SudokuRule removePossibilitiesRule = new RemovePossibilitiesRule();
    private final SudokuRule nakedSingleRule = new NakedSingleRule();
    private final SudokuRule pointingPairRule = new PointingPairRule();
    private final SudokuRule nakedPairRule = new NakedPairRule();
    private final SudokuRule hiddenSingleRule = new HiddenSingleRule();

    @Override
    public SudokuGrid solve(SudokuGrid grid) {
        int iteration = 0;
        while (!grid.isSolved()) {
            System.out.println("Running iteration " + iteration);

            if (iteration > 2000) {
                // Puzzle is unsolvable or broken
                break;
            }


            iteration++;
            if (removePossibilitiesRule.apply(grid)) {
                continue;
            }
            if (nakedSingleRule.apply(grid)) {
                continue;
            }
            if (hiddenSingleRule.apply(grid)) {
                continue;
            }
            if (nakedPairRule.apply(grid)) {
                continue;
            }
            if (pointingPairRule.apply(grid)) {
                continue;
            }

            System.out.println("Could not find anymore solutions");
            break;
        }
        return grid;
    }
}
