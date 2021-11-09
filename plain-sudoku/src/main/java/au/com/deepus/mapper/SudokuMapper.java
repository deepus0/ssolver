package au.com.deepus.mapper;

import au.com.deepus.exception.InvalidGridException;
import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.StandardSudokuGrid;

import java.util.ArrayList;

import static au.com.deepus.helper.SudokuConstants.SUDOKU_COUNT;

/**
 * Maps an input to a Sudoku Grid
 */
public class SudokuMapper {

    public StandardSudokuGrid mapStandardSudokuGrid(String singleLine) {
        if (singleLine.length() != (SUDOKU_COUNT * SUDOKU_COUNT)) {
            throw new InvalidGridException();
        }

        var cells = new ArrayList<SudokuCell>();
        for (int i = 0; i < singleLine.length(); i++) {
            char c = singleLine.charAt(i);
            var number = Character.getNumericValue(c) == -1 ? 0 : Character.getNumericValue(c);
            var cell = new SudokuCell(i, number, i / SUDOKU_COUNT, i % SUDOKU_COUNT);
            cells.add(cell);
        }
        return new StandardSudokuGrid(cells);
    }
}
