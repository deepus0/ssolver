package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;
import au.com.deepus.models.grid.SudokuGridStandard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PointingPairRule implements SudokuRule {

    private final List<Integer> ROW_1 = Arrays.asList(0, 1, 2);
    private final List<Integer> ROW_2 = Arrays.asList(3, 4, 5);
    private final List<Integer> ROW_3 = Arrays.asList(6, 7, 8);
    private final List<Integer> COL_1 = Arrays.asList(0, 3, 6);
    private final List<Integer> COL_2 = Arrays.asList(1, 4, 7);
    private final List<Integer> COL_3 = Arrays.asList(2, 5, 8);
    private final List<List<Integer>> COMBINATIONS = Arrays.asList(ROW_1, ROW_2, ROW_3, COL_1, COL_2, COL_3);

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;

        // Pointing Pair / Triple
        for (List<SudokuCell> box : grid.getBoxes()) {
            for (int i = 1; i <= 9; i++) {
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
                        if (removeRowPossibilities(grid, finalI, candidateCells.get(0))) {
                            var cell = candidateCells.get(0);
                            grid.addStep("Found Pointing Pair/Triple " + finalI + " in row " + (cell.getRow() + 1));
                            changed = true;
                        }
                    } else if (COL_1.containsAll(candidates) || COL_2.containsAll(candidates) || COL_3.containsAll(candidates)) {
                        if (removeColPossibilities(grid, finalI, candidateCells.get(0))) {
                            var cell = candidateCells.get(0);
                            grid.addStep("Found Pointing Pair/Triple " + finalI + " in col " + (cell.getCol() + 1));
                            changed = true;
                        }
                    }
                }
            }
        }

        // Box Line Reduction
        return changed;
    }

    private boolean removeRowPossibilities(SudokuGrid grid, int number, SudokuCell candidateCell) {
        boolean changed = false;
        for (SudokuCell cell : grid.getRow(candidateCell.getRow())) {
            if (candidateCell.getBox() != cell.getBox()) {
                if (cell.getPossibilities().removeAll(List.of(number))) {
                    changed = true;
                }
            }

        }
        return changed;
    }

    private boolean removeColPossibilities(SudokuGrid grid, int number, SudokuCell candidateCell) {
        boolean changed = false;
        for (SudokuCell cell : grid.getCol(candidateCell.getCol())) {
            if (candidateCell.getBox() != cell.getBox()) {
                if (cell.getPossibilities().removeAll(List.of(number))) {
                    changed = true;
                }
            }

        }
        return changed;
    }
}
