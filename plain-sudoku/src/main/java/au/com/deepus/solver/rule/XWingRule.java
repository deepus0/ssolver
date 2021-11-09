package au.com.deepus.solver.rule;

import au.com.deepus.helper.SudokuEnum;
import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static au.com.deepus.helper.SudokuConstants.SUDOKU_COUNT;

public class XWingRule implements SudokuRule {

    private boolean isChanged;

    @Override
    public boolean apply(SudokuGrid grid) {
        this.isChanged = false;
        findXWings(grid, SudokuEnum.ROW);
        findXWings(grid, SudokuEnum.COL);
        return isChanged;
    }

    private void findXWings(SudokuGrid grid, SudokuEnum type) {
        for (int number = 1; number <= SUDOKU_COUNT; number++) {
            var matrices = new ArrayList<List<Boolean>>();
            for (List<SudokuCell> cells : grid.getRows()) {
                var matrix = new ArrayList<Boolean>();
                for (SudokuCell cell : cells) {
                    matrix.add(!cell.isPopulated() && cell.getPossibilities().contains(number));
                }
                matrices.add(matrix);
            }

            matrixLoop:
            for (int i = 0; i < matrices.size(); i++) {
                if (!matrices.get(i).contains(true)) {
                    continue;
                }
                for (int j = i + 1; j < matrices.size(); j++) {
                    if (matrices.get(i).equals(matrices.get(j))) {
                        findMatrixIndexes(grid, number, matrices.get(i), i, j, type);
                        break matrixLoop;
                    }
                }
            }
        }
    }

    private void findMatrixIndexes(SudokuGrid grid, int number, List<Boolean> matrix, int firstFoundIndex, int secondFoundIndex, SudokuEnum type) {
        int firstIndex = -1, secondIndex = -1;
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i)) {
                if (firstIndex == -1) {
                    firstIndex = i;
                } else {
                    secondIndex = i;
                    break;
                }
            }
        }

        if (type.equals(SudokuEnum.ROW)) {
            removePossibilities(grid, number, firstFoundIndex, secondFoundIndex, grid.getCol(firstIndex));
            removePossibilities(grid, number, firstFoundIndex, secondFoundIndex, grid.getCol(secondIndex));
        } else if (type.equals(SudokuEnum.COL)) {
            removePossibilities(grid, number, firstFoundIndex, secondFoundIndex, grid.getRow(firstIndex));
            removePossibilities(grid, number, firstFoundIndex, secondFoundIndex, grid.getRow(secondIndex));
        }
    }

    private void removePossibilities(SudokuGrid grid, int number, int firstIndex, int secondIndex, List<SudokuCell> cells) {
        for (int i = 0; i < SUDOKU_COUNT; i++) {
            if (i != firstIndex && i != secondIndex) {
                if (cells.get(i).getPossibilities().contains(number)) {
                    cells.get(i).getPossibilities().removeAll(Collections.singleton(number));
                    grid.addStep("Found X Wing - Removed number " + number);
                    this.isChanged = true;
                }
            }
        }
    }
}
