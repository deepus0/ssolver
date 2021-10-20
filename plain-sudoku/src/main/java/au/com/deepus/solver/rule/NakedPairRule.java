package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.SudokuGrid;

import java.util.List;
import java.util.stream.Collectors;

public class NakedPairRule implements SudokuRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;

        // Pointing Pair / Triple
        for (List<SudokuCell> box : grid.getBoxes(false)) {
            for (int i = 1; i <= 9; i++) {
                boolean isPair = false;
                int finalI = i;
                List<SudokuCell> cells = box.stream().filter(cell -> cell.getPossibilities().contains(finalI)).collect(Collectors.toList());
                if (cells.size() == 2) {

                }
            }
        }


        // Box Line Reduction
        return changed;
    }
}
