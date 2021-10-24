package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.List;

public class NakedSingleRule implements SudokuRule {

    private boolean isChanged;

    @Override
    public boolean apply(SudokuGrid grid) {
        this.isChanged = false;

        for (List<SudokuCell> row : grid.getRows()) {
            for (SudokuCell cell : row) {
                if (!cell.isPopulated() && cell.getPossibilities().size() == 1) {
                    cell.setAllocated(cell.getPossibilities().get(0));
                    cell.setPossibilities(new ArrayList<>());
                    grid.addStep("Found Naked Single " + cell.getAllocated() + " in cell " + cell.getCellName());
                    this.isChanged = true;
                }
            }
        }
        return isChanged;
    }
}
