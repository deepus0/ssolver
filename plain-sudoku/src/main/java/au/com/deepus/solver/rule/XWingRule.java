package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static au.com.deepus.helper.SudokuConstants.SUDOKU_COUNT;

public class XWingRule implements SudokuRule {

    private boolean isChanged;

    @Override
    public boolean apply(SudokuGrid grid) {
        this.isChanged = false;
        // Go through all the numbers
        // Go through all the rows, then columns

        for (int number = 1; number <= SUDOKU_COUNT; number++) {
            var matrices = new ArrayList<List<Boolean>>();
            for (List<SudokuCell> cells : grid.getRows()) {
                var rowMatrix = new ArrayList<Boolean>();
                for (SudokuCell cell : cells) {
                    rowMatrix.add(!cell.isPopulated() && cell.getPossibilities().contains(number));
                }
                matrices.add(rowMatrix);
            }

            matrixLoop:
            for (int i = 0; i < matrices.size(); i++) {
                if (!matrices.get(i).contains(true)) {
                    continue;
                }
                for (int j = i + 1; j < matrices.size(); j++) {
                    if (matrices.get(i).equals(matrices.get(j))) {
                        removePossibilities(grid, number, matrices.get(i), i, matrices.get(j), j);
                        this.isChanged = true;
                        break matrixLoop;
                    }
                }
            }
        }

        return isChanged;
    }

    private boolean removePossibilities(SudokuGrid grid, int number, List<Boolean> firstRow, int rowId, List<Boolean> secondRow, int secondRowId) {
        boolean changed = false;
        var cols = grid.getCols();
        int firstColId = -1, secondColId = -1;
        for (int i = 0; i < firstRow.size(); i++) {
            if (firstRow.get(i)) {
                if (firstColId == -1) {
                    firstColId = i;
                } else {
                    secondColId = i;
                    break;
                }
            }
        }

        var firstCol = grid.getCol(firstColId);
        for (int i = 0; i < SUDOKU_COUNT; i++) {
            if (i != rowId && i != secondRowId) {
                if (firstCol.get(i).getPossibilities().contains(number)) {
                    firstCol.get(i).getPossibilities().removeAll(Collections.singleton(number));
                    changed = true;
                }
            }
        }

        var secondCol = grid.getCol(secondColId);
        for (int i = 0; i < SUDOKU_COUNT; i++) {
            if (i != rowId && i != secondRowId) {
                if (secondCol.get(i).getPossibilities().contains(number)) {
                    secondCol.get(i).getPossibilities().removeAll(Collections.singleton(number));
                    changed = true;
                }
            }
        }

        return changed;
    }
}
