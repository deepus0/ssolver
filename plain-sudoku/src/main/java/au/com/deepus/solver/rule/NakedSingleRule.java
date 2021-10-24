package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;
import au.com.deepus.models.grid.SudokuGridStandard;

import java.util.ArrayList;
import java.util.List;

public class NakedSingleRule implements SudokuRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;

        for (List<SudokuCell> row : grid.getRows()) {
            for (SudokuCell cell : row) {
                if (!cell.isPopulated() && cell.getPossibilities().size() == 1) {
                    cell.setAllocated(cell.getPossibilities().get(0));
                    cell.setPossibilities(new ArrayList<>());
                    grid.addStep("Found Naked Single " + cell.getAllocated() + " in cell " + cell.getCellName());
                    changed = true;
                }
            }
        }
        return changed;
    }
}
