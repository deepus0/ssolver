package au.com.deepus.mapper;

import au.com.deepus.exception.InvalidGridException;
import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.SudokuGrid;

import java.util.ArrayList;

/**
 * Maps an input to a Sudoku Grid
 */
public class SudokuMapper {

    public SudokuGrid map(String singleLine) {
        if (singleLine.length() != 81) {
            throw new InvalidGridException();
        }

        var grid = new SudokuGrid();

        var row = new ArrayList<SudokuCell>();
        for (int i = 0; i < singleLine.length(); i++) {
            if (i % 9 == 0 && i != 0) {
                grid.addRow(row);
                row = new ArrayList<>();
            }
            char c = singleLine.charAt(i);
            var number = Character.getNumericValue(c);;
            var cell = new SudokuCell(i, number, grid.getCells().size(), i % 9);
            row.add(cell);
        }
        grid.addRow(row);
        return grid;
    }
}
