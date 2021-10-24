package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NakedCandidatesRule implements SudokuRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean changed = false;

        if (findCandidateCells(grid.getBoxes(), grid)) {
            changed = true;
        }
        if (findCandidateCells(grid.getRows(), grid)) {
            changed = true;
        }
        if (findCandidateCells(grid.getCols(), grid)) {
            changed = true;
        }
        return changed;
    }

    private boolean findCandidateCells(List<List<SudokuCell>> cells, SudokuGrid grid) {
        boolean changed = false;
        for (List<SudokuCell> arr : cells) {
            if (findCandidates(arr, grid, 2)) {
                changed = true;
            }
            if (findCandidates(arr, grid, 3)) {
                changed = true;
            }
            if (findCandidates(arr, grid, 4)) {
                changed = true;
            }
        }
        return changed;
    }

    private boolean findCandidates(List<SudokuCell> arr, SudokuGrid grid, int count) {
        boolean changed = false;
        var pairs = arr.stream()
                .filter(cell -> cell.getPossibilities().size() == count)
                .collect(Collectors.toList());

        for (SudokuCell pairCell : pairs) {
            var matches = new ArrayList<SudokuCell>();
            for (SudokuCell cell : arr) {
                if (!cell.isPopulated() && pairCell.getPossibilities().containsAll(cell.getPossibilities())) {
                    matches.add(cell);
                }
            }
            if (matches.size() == count) {
                for (SudokuCell cell : arr) {
                    if (!cell.isPopulated() && !matches.contains(cell) && cell.getPossibilities().removeAll(matches.get(0).getPossibilities())) {
                        grid.addStep("Added Candidate Pair/Triple/Quad rule on " + matches.get(0).getPossibilities());
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }
}
