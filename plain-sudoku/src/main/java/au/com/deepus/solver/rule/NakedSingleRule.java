package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.SudokuGrid;

import java.util.ArrayList;
import java.util.List;

public class NakedSingleRule implements SudokuRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;

        for (List<SudokuCell> row : grid.getCells()) {
            for (SudokuCell cell : row) {
                if (!cell.isPopulated() && cell.getPossibilities().size() == 1) {
                    cell.setAllocated(cell.getPossibilities().get(0));
                    cell.setPossibilities(new ArrayList<>());
                    changed = true;
                }
            }
        }
        return changed;
    }
}
