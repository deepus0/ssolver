package au.com.deepus.solver.rule;

import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NakedCandidatesRule implements SudokuRule {

    private boolean isChanged;

    @Override
    public boolean apply(SudokuGrid grid) {
        this.isChanged = false;
        findCandidateCells(grid.getBoxes(), grid);
        findCandidateCells(grid.getRows(), grid);
        findCandidateCells(grid.getCols(), grid);
        return this.isChanged;
    }

    private void findCandidateCells(List<List<SudokuCell>> cells, SudokuGrid grid) {
        for (List<SudokuCell> arr : cells) {
            findCandidates(arr, grid, 2);
            findCandidates(arr, grid, 3);
            findCandidates(arr, grid, 4);
        }
    }

    private void findCandidates(List<SudokuCell> arr, SudokuGrid grid, int count) {
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
                        this.isChanged = true;
                    }
                }
            }
        }
    }
}
