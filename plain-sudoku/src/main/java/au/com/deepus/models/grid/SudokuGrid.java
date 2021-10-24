package au.com.deepus.models.grid;

import au.com.deepus.models.SudokuCell;

import java.util.List;

public interface SudokuGrid {

    SudokuCell getCell(int cellId);
    SudokuCell getCell(int rowId, int colId);

    List<List<SudokuCell>> getRows();
    List<SudokuCell> getRow(int rowId);
    List<SudokuCell> getRow(SudokuCell cell);

    List<List<SudokuCell>> getCols();
    List<SudokuCell> getCol(int colId);
    List<SudokuCell> getCol(SudokuCell cell);

    List<List<SudokuCell>> getBoxes();
    List<SudokuCell> getBox(int boxId);
    List<SudokuCell> getBox(SudokuCell cell);

    List<String> getSteps();
    void addStep(String step);
    int getIterationCount();
    void setIterationCount(int iteration);

    boolean isSolved();
}
