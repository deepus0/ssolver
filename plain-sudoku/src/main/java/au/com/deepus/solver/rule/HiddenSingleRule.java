package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.List;

public class HiddenSingleRule implements SudokuRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;

        for (int i = 1; i <= 9; i++) {
            if (findCandidates(grid, grid.getRows(), i, "Row")) {
                changed = true;
            }
            if (findCandidates(grid, grid.getCols(), i, "Column")) {
                changed = true;
            }
            if (findCandidates(grid, grid.getBoxes(), i, "Box")) {
                changed = true;
            }
        }
        return changed;
    }

    private boolean findCandidates(SudokuGrid grid, List<List<SudokuCell>> cells, int number, String name) {
        boolean changed = false;
        for (List<SudokuCell> row : cells) {
            boolean containsAllocated = false;
            for (SudokuCell cell : row) {
                if (cell.getAllocated() == number) {
                    containsAllocated = true;
                    break;
                }
            }
            if (!containsAllocated) {
                var candidateCells = new ArrayList<SudokuCell>();
                for (SudokuCell cell : row) {
                    if (cell.getPossibilities().contains(number)) {
                        candidateCells.add(cell);
                    }
                }
                if (candidateCells.size() == 1) {
                    var cell = candidateCells.get(0);
                    cell.setAllocated(number);
                    grid.addStep("Found Hidden Single in " + name + " value " + cell.getAllocated() + " in cell " + cell.getCellName());
                    changed = true;
                }
            }
        }
        return changed;
    }
}
