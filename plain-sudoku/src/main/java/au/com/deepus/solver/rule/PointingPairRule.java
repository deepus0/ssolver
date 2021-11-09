package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static au.com.deepus.helper.SudokuConstants.SUDOKU_COUNT;

public class PointingPairRule implements SudokuRule {

    private boolean isChanged;

    private final List<Integer> ROW_1 = Arrays.asList(0, 1, 2);
    private final List<Integer> ROW_2 = Arrays.asList(3, 4, 5);
    private final List<Integer> ROW_3 = Arrays.asList(6, 7, 8);
    private final List<Integer> COL_1 = Arrays.asList(0, 3, 6);
    private final List<Integer> COL_2 = Arrays.asList(1, 4, 7);
    private final List<Integer> COL_3 = Arrays.asList(2, 5, 8);

    @Override
    public boolean apply(SudokuGrid grid) {
        this.isChanged = false;
        // Pointing Pair / Triple
        for (List<SudokuCell> box : grid.getBoxes()) {
            for (int i = 1; i <= SUDOKU_COUNT; i++) {
                final int finalI = i;
                boolean boxContainsNumber = box.stream().map(SudokuCell::getAllocated).collect(Collectors.toList()).contains(finalI);
                if (!boxContainsNumber) {
                    List<SudokuCell> candidateCells = box.stream()
                            .filter(cell -> cell.getPossibilities().contains(finalI))
                            .collect(Collectors.toList());
                    List<Integer> candidates = box.stream()
                            .filter(cell -> cell.getPossibilities().contains(finalI))
                            .map(SudokuCell::getBoxCell)
                            .collect(Collectors.toList());
                    if (candidates.isEmpty() || candidateCells.isEmpty()) {
                        continue;
                    }
                    if (ROW_1.containsAll(candidates) || ROW_2.containsAll(candidates) || ROW_3.containsAll(candidates)) {
                        removeRowPossibilities(grid, finalI, candidateCells.get(0));
                    } else if (COL_1.containsAll(candidates) || COL_2.containsAll(candidates) || COL_3.containsAll(candidates)) {
                        removeColPossibilities(grid, finalI, candidateCells.get(0));
                    }
                }
            }
        }
        return this.isChanged;
    }

    private void removeRowPossibilities(SudokuGrid grid, int number, SudokuCell candidateCell) {
        for (SudokuCell cell : grid.getRow(candidateCell.getRow())) {
            if (candidateCell.getBox() != cell.getBox() && cell.getPossibilities().removeAll(List.of(number))) {
                grid.addStep("Found Pointing Pair/Triple " + number + " in row " + (candidateCell.getRow() + 1));
                this.isChanged = true;
            }
        }
    }

    private void removeColPossibilities(SudokuGrid grid, int number, SudokuCell candidateCell) {
        for (SudokuCell cell : grid.getCol(candidateCell.getCol())) {
            if (candidateCell.getBox() != cell.getBox() && cell.getPossibilities().removeAll(List.of(number))) {
                grid.addStep("Found Pointing Pair/Triple " + number + " in col " + (candidateCell.getCol() + 1));
                this.isChanged = true;
            }
        }
    }
}
