package au.com.deepus.models.grid;

import au.com.deepus.models.SudokuCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 */
public class SudokuGridV2 implements SudokuGrid {

    private final List<SudokuCell> cells;
    private final List<String> steps;
    private int iterationCount;

    public SudokuGridV2() {
        this.cells = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public void addCell(SudokuCell cell) {
        this.cells.add(cell);
    }

    @Override
    public SudokuCell getCell(int cellId) {
        return this.cells.get(cellId);
    }

    @Override
    public SudokuCell getCell(int rowId, int colId) {
        return cells.stream()
                .filter(cell -> cell.getRow() == rowId && cell.getCol() == colId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<List<SudokuCell>> getRows() {
        var rows = new ArrayList<List<SudokuCell>>();
        for (int i = 0; i < 9; i++) {
            rows.add(new ArrayList<>());
        }
        for (SudokuCell cell : this.cells) {
            rows.get(cell.getRow()).add(cell);
        }
        return rows;
    }

    @Override
    public List<SudokuCell> getRow(int rowId) {
        return this.cells.stream().filter(cell -> cell.getRow() == rowId).collect(Collectors.toList());
    }

    @Override
    public List<SudokuCell> getRow(SudokuCell cell) {
        return this.getRow(cell.getRow());
    }

    @Override
    public List<List<SudokuCell>> getCols() {
        var cols = new ArrayList<List<SudokuCell>>();
        for (int i = 0; i < 9; i++) {
            cols.add(new ArrayList<>());
        }
        for (SudokuCell cell : this.cells) {
            cols.get(cell.getCol()).add(cell);
        }
        return cols;
    }

    @Override
    public List<SudokuCell> getCol(int colId) {
        return this.cells.stream().filter(cell -> cell.getCol() == colId).collect(Collectors.toList());
    }

    @Override
    public List<SudokuCell> getCol(SudokuCell cell) {
        return this.getCol(cell.getId());
    }

    @Override
    public List<List<SudokuCell>> getBoxes() {
        var boxes = new ArrayList<List<SudokuCell>>();
        for (int i = 0; i < 9; i++) {
            boxes.add(new ArrayList<>());
        }
        for (SudokuCell cell : this.cells) {
            boxes.get(cell.getBox()).add(cell);
        }
        return boxes;
    }

    @Override
    public List<SudokuCell> getBox(int boxId) {
        return this.cells.stream().filter(cell -> cell.getBox() == boxId).collect(Collectors.toList());
    }

    @Override
    public List<SudokuCell> getBox(SudokuCell cell) {
        return this.getBox(cell.getBox());
    }

    @Override
    public List<String> getSteps() {
        return this.steps;
    }

    @Override
    public int getIterationCount() {
        return this.iterationCount;
    }

    @Override
    public void addStep(String step) {
        this.steps.add(step);
    }

    @Override
    public void setIterationCount(int iteration) {
        this.iterationCount = iteration;
    }

    @Override
    public boolean isSolved() {
        for (SudokuCell cell : cells) {
            if (!cell.isPopulated()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SudokuGridV2)) return false;
        SudokuGridV2 that = (SudokuGridV2) o;
        return Objects.equals(cells, that.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Sudoku Grid: (iteration count - " + iterationCount + ") \n");
        s.append("+-----------+-----------+-----------+\n");
        for (List<SudokuCell> row : this.getRows()) {
            for (int i = 0; i < row.size(); i++) {
                if (i == 0) {
                    s.append("| ");
                }
                s.append(row.get(i).isPopulated() ? row.get(i).getAllocated() : " ").append((i + 1) % 3 == 0 ? " | " : "   ");
            }
            s.append("\n");
            if ((row.get(0).getRow() + 1) % 3 != 0) {
                s.append("|           |           |           |\n");
            } else if ((row.get(0).getRow()) > 1 && row.get(0).getRow() < 8) {
                s.append("|-----------+-----------+-----------|\n");
            } else {
                s.append("+-----------+-----------+-----------+\n");
            }

        }
        return s.toString();
    }
}
