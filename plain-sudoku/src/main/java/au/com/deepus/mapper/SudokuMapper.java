package au.com.deepus.mapper;

import au.com.deepus.exception.InvalidGridException;
import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.StandardSudokuGrid;

import java.util.ArrayList;

/**
 * Maps an input to a Sudoku Grid
 */
public class SudokuMapper {

    public StandardSudokuGrid mapStandardSudokuGrid(String singleLine) {
        if (singleLine.length() != 81) {
            throw new InvalidGridException();
        }

        var cells = new ArrayList<SudokuCell>();
        for (int i = 0; i < singleLine.length(); i++) {
            char c = singleLine.charAt(i);
            var number = Character.getNumericValue(c) == -1 ? 0 : Character.getNumericValue(c);
            var cell = new SudokuCell(i, number, i / 9, i % 9);
            cells.add(cell);
        }
        return new StandardSudokuGrid(cells);
    }
}
