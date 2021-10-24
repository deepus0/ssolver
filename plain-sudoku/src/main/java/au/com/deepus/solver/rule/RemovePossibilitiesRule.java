package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.List;

public class RemovePossibilitiesRule implements SudokuRule {

    private boolean isChanged;

    @Override
    public boolean apply(SudokuGrid grid) {
        this.isChanged = false;
        findPossibilities(grid, grid.getRows());
        findPossibilities(grid, grid.getCols());
        findPossibilities(grid, grid.getBoxes());
        return isChanged;
    }

    private void findPossibilities(SudokuGrid grid, List<List<SudokuCell>> arr) {
        for (List<SudokuCell> cells : arr) {
            for (SudokuCell cell : cells) {
                if (cell.isPopulated()) {
                    for (SudokuCell cell2 : cells) {
                        if (cell.getId() != cell2.getId() && !cell2.isPopulated() && cell2.getPossibilities().contains(cell.getAllocated())) {
                            cell2.getPossibilities().remove((Integer) cell.getAllocated());
                            if (grid.getIterationCount() > 0) {
                                grid.addStep("Removed Possibility " + cell.getAllocated() + " from " + cell2.getCellName());
                            }
                            this.isChanged = true;
                        }
                    }
                }
            }
        }
    }
}
