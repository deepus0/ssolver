package au.com.deepus.mapper;

import au.com.deepus.exception.InvalidGridException;
import au.com.deepus.models.SudokuCell;
import au.com.deepus.models.grid.SudokuGridStandard;
import au.com.deepus.models.grid.SudokuGridV2;

import java.util.ArrayList;

/**
 * Maps an input to a Sudoku Grid
 */
public class SudokuMapper {

    public SudokuGridStandard mapStandard(String singleLine) {
        if (singleLine.length() != 81) {
            throw new InvalidGridException();
        }

        var grid = new SudokuGridStandard();

        var row = new ArrayList<SudokuCell>();
        for (int i = 0; i < singleLine.length(); i++) {
            if (i % 9 == 0 && i != 0) {
                grid.addRow(row);
                row = new ArrayList<>();
            }
            char c = singleLine.charAt(i);
            var number = Character.getNumericValue(c) == -1 ? 0 : Character.getNumericValue(c);
            var cell = new SudokuCell(i, number, grid.getCells().size(), i % 9);
            row.add(cell);
        }
        grid.addRow(row);
        return grid;
    }

    public SudokuGridV2 mapV2(String singleLine) {
        if (singleLine.length() != 81) {
            throw new InvalidGridException();
        }

        var grid = new SudokuGridV2();

        var row = new ArrayList<SudokuCell>();
        for (int i = 0; i < singleLine.length(); i++) {
            char c = singleLine.charAt(i);
            var number = Character.getNumericValue(c) == -1 ? 0 : Character.getNumericValue(c);
            var cell = new SudokuCell(i, number, i / 9, i % 9);
            grid.addCell(cell);
        }
        return grid;
    }
}
