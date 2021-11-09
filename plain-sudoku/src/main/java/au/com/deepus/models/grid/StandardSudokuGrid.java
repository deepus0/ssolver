package au.com.deepus.models.grid;

import au.com.deepus.models.SolvedStep;
import au.com.deepus.models.SudokuCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static au.com.deepus.helper.SudokuConstants.SUDOKU_COUNT;

public class StandardSudokuGrid implements SudokuGrid {

    private final List<SudokuCell> cells;
    private final List<SolvedStep> steps;
    private int iterationCount;
    private final List<List<SudokuCell>> rows;
    private final List<List<SudokuCell>> cols;
    private final List<List<SudokuCell>> boxes;

    public StandardSudokuGrid(List<SudokuCell> cells) {
        this.cells = cells;
        this.steps = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.cols = new ArrayList<>();
        this.boxes = new ArrayList<>();

        for (int i = 0; i < SUDOKU_COUNT; i++) {
            rows.add(new ArrayList<>());
            cols.add(new ArrayList<>());
            boxes.add(new ArrayList<>());
        }
        for (SudokuCell cell : this.cells) {
            rows.get(cell.getRow()).add(cell);
            cols.get(cell.getCol()).add(cell);
            boxes.get(cell.getBox()).add(cell);
        }
    }

    @Override
    public SudokuCell getCell(int cellId) {
        return this.cells.get(cellId);
    }

    @Override
    public SudokuCell getCell(int rowId, int colId) {
        for (SudokuCell cell : cells) {
            if (cell.getRow() == rowId && cell.getCol() == colId) {
                return cell;
            }
        }
        return null;
    }

    @Override
    public List<List<SudokuCell>> getRows() {
        return this.rows;
    }

    @Override
    public List<SudokuCell> getRow(int rowId) {
        return this.rows.get(rowId);
    }

    @Override
    public List<SudokuCell> getRow(SudokuCell cell) {
        return this.getRow(cell.getRow());
    }

    @Override
    public List<List<SudokuCell>> getCols() {
        return this.cols;
    }

    @Override
    public List<SudokuCell> getCol(int colId) {
        return this.cols.get(colId);
    }

    @Override
    public List<SudokuCell> getCol(SudokuCell cell) {
        return this.getCol(cell.getId());
    }

    @Override
    public List<List<SudokuCell>> getBoxes() {
        return this.boxes;
    }

    @Override
    public List<SudokuCell> getBox(int boxId) {
        return this.boxes.get(boxId);
    }

    @Override
    public List<SudokuCell> getBox(SudokuCell cell) {
        return this.getBox(cell.getBox());
    }

    @Override
    public List<String> getSteps() {
        return this.steps.stream().map(SolvedStep::getDescription).collect(Collectors.toList());
    }

    @Override
    public int getIterationCount() {
        return this.iterationCount;
    }

    @Override
    public void addStep(String step) {
        this.steps.add(new SolvedStep(step));
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
        if (!(o instanceof StandardSudokuGrid)) return false;
        StandardSudokuGrid that = (StandardSudokuGrid) o;
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
