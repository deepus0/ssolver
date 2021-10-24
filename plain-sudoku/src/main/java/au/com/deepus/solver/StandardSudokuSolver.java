package au.com.deepus.solver;

import au.com.deepus.exception.InvalidGridException;
import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;
import au.com.deepus.solver.rule.HiddenSingleRule;
import au.com.deepus.solver.rule.NakedCandidatesRule;
import au.com.deepus.solver.rule.NakedSingleRule;
import au.com.deepus.solver.rule.PointingPairRule;
import au.com.deepus.solver.rule.RemovePossibilitiesRule;
import au.com.deepus.solver.rule.SudokuRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StandardSudokuSolver implements SudokuSolver {

    /**
     * Defines the order to run the rules list
     */
    private final List<SudokuRule> rulesList = Arrays.asList(
            new RemovePossibilitiesRule(),
            new NakedSingleRule(),
            new HiddenSingleRule(),
            new NakedCandidatesRule(),
            new PointingPairRule()
    );

    @Override
    public SudokuGrid solve(SudokuGrid grid) {
        int iteration = 0;

        ruleLoop:
        while (!grid.isSolved() && iteration < 2000) {
            if (!validateGrid(grid)) {
                grid.getSteps().forEach(System.out::println);
                System.out.println(grid);
                throw new InvalidGridException();
            }
            iteration++;
            for (SudokuRule rule : rulesList) {
                if (rule.apply(grid)) {
                    grid.setIterationCount(iteration);
                    continue ruleLoop;
                }
            }

            System.out.println("Could not find anymore solutions");
            break;
        }
        grid.setIterationCount(iteration);
        return grid;
    }

    private boolean validateGrid(SudokuGrid grid) {
        return validateCells(grid.getRows()) && validateCells(grid.getCols()) && validateCells(grid.getBoxes());
    }

    private boolean validateCells(List<List<SudokuCell>> arr) {
        for (List<SudokuCell> row : arr) {
            var cells = row.stream().filter(SudokuCell::isPopulated).map(SudokuCell::getAllocated).collect(Collectors.toList());
            Set<Integer> set = new HashSet<>(cells);
            if (set.size() < cells.size()) {
                System.out.println("Invalid row/column/box " + arr.indexOf(row));
                return false;
            }
        }
        return true;
    }
}
