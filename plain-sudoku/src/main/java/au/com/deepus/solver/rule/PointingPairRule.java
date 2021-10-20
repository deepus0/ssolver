package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.SudokuGrid;

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
        for (List<SudokuCell> box : grid.getAllBoxes()) {
            for (int i = 1; i <= box.size(); i++) {
                final int finalI = i;
                int boxId = box.get(0).getBox();
                boolean boxContainsNumber = box.stream().map(SudokuCell::getAllocated).collect(Collectors.toList()).contains(finalI);
                if (!boxContainsNumber) {
                    List<SudokuCell> candidateCells = box.stream()
                            .filter(cell -> cell.getPossibilities().contains(finalI))
                            .collect(Collectors.toList());
                    List<Integer> candidates = box.stream()
                            .filter(cell -> cell.getPossibilities().contains(finalI))
                            .map(SudokuCell::getBoxCell)
                            .collect(Collectors.toList());
                    if (candidates.size() == 0 || candidateCells.size() == 0) {
                        continue;
                    }
                    if (ROW_1.containsAll(candidates) || ROW_2.containsAll(candidates) || ROW_3.containsAll(candidates)) {
                        if (removeRowPossibilities(grid, finalI, candidateCells.get(0).getRow())) {
                            changed = true;
                        }
                    } else if (COL_1.containsAll(candidates) || COL_2.containsAll(candidates) || COL_3.containsAll(candidates)) {
                        if (removeColPossibilities(grid, finalI, candidateCells.get(0).getCol())) {
                            changed = true;
                        }
                    }
                }
            }
        }

        // Box Line Reduction
        return changed;
    }

    private boolean removeRowPossibilities(SudokuGrid grid, int number, int row) {
        for (SudokuCell cells : grid.getRow(row)) {
            return cells.getPossibilities().removeAll(List.of(number));
        }
        return false;
    }

    private boolean removeColPossibilities(SudokuGrid grid, int number, int col) {
        for (SudokuCell cells : grid.getCol(col)) {
            return cells.getPossibilities().removeAll(List.of(number));
        }
        return false;
    }
}
