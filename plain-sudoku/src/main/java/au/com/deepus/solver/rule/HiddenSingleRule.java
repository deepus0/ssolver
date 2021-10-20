package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.SudokuGrid;

import java.util.ArrayList;
import java.util.List;

public class HiddenSingleRule implements SudokuRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;

        for (int i = 1; i <= 9; i++) {
            if (findCandidates(grid.getRows(), i)) {
                changed = true;
            }
            if (findCandidates(grid.getCols(), i)) {
                changed = true;
            }
            if (findCandidates(grid.getAllBoxes(), i)) {
                changed = true;
            }
        }
        return changed;
    }

    private boolean findCandidates(List<List<SudokuCell>> cells, int number) {
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
                    candidateCells.get(0).setAllocated(number);
                    changed = true;
                }
            }
        }
        return changed;
    }
}
