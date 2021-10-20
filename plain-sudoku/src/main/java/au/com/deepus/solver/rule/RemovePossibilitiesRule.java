package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.SudokuGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RemovePossibilitiesRule implements SudokuRule {

    private final List<Integer> POSSIBLE_VALUES = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;
        for (int i = 0; i < grid.getCells().size(); i++) {
            for (int j = 0; j < grid.getCells().get(i).size(); j++) {
                var cell = grid.getCell(i, j);
                if (!cell.isPopulated()) {
                    if (findPossibilities(grid, cell)) {
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    private boolean findPossibilities(SudokuGrid grid, SudokuCell cell) {
        if (cell.getPossibilities().isEmpty()) {
            cell.setPossibilities(new ArrayList<>(POSSIBLE_VALUES));
        }

        boolean changed = false;

        /**
         * Check rows
         */
        for (SudokuCell c :  grid.getRow(cell)) {
            if (c.isPopulated()) {
                var index = cell.getPossibilities().indexOf(c.getAllocated());
                if (index != -1) {
                    cell.getPossibilities().remove(index);
                    changed = true;
                }

            }
        }

        /**
         * Check columns
         */
        for (SudokuCell c : grid.getCol(cell)) {
            if (c.isPopulated()) {
                var index = cell.getPossibilities().indexOf(c.getAllocated());
                if (index != -1) {
                    cell.getPossibilities().remove(index);
                    changed = true;
                }

            }
        }
        /**
         * Check 9 boxes
         */
        for (SudokuCell c : grid.getBox(cell)) {
            if (c.isPopulated()) {
                var index = cell.getPossibilities().indexOf(c.getAllocated());
                if (index != -1) {
                    cell.getPossibilities().remove(index);
                    changed = true;
                }

            }
        }

        return changed;
    }
}
