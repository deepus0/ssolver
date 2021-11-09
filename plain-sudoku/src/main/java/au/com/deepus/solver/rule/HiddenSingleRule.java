package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.List;

import static au.com.deepus.helper.SudokuConstants.SUDOKU_COUNT;

public class HiddenSingleRule implements SudokuRule {

    private boolean isChanged;

    @Override
    public boolean apply(SudokuGrid grid) {
        this.isChanged = false;

        for (int i = 1; i <= SUDOKU_COUNT; i++) {
            findCandidates(grid, grid.getRows(), i, "Row");
            findCandidates(grid, grid.getCols(), i, "Column");
            findCandidates(grid, grid.getBoxes(), i, "Box");
        }
        return isChanged;
    }

    private void findCandidates(SudokuGrid grid, List<List<SudokuCell>> cells, int number, String name) {
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
                    this.isChanged = true;
                }
            }
        }
    }
}
