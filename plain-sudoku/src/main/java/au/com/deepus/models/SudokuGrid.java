package au.com.deepus.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class SudokuGrid {

    private List<List<SudokuCell>> cells;

    public SudokuGrid() {
        this.cells = new ArrayList<>();
    }

    public void addRow(List<SudokuCell> row) {
        this.cells.add(row);
    }

    public SudokuCell getCell(int row, int col) {
        return this.cells.get(row).get(col);
    }

    public List<List<SudokuCell>> getRows() {
        return this.cells;
    }

    public List<List<SudokuCell>> getCols() {
        var cols = new ArrayList<List<SudokuCell>>();
        for (int i = 0; i < 9; i++) {
            var col = new ArrayList<SudokuCell>();
            for (int j = 0; j < 9; j++) {
                col.add(cells.get(i).get(j));
            }
            cols.add(col);
        }
        return cols;
    }

    public SudokuCell getCell(int id) {
        for (List<SudokuCell> row : cells) {
            for (SudokuCell cell : row) {
                if (cell.getId() == id) {
                    return cell;
                }
            }
        }
        return null;
    }

    public List<SudokuCell> getRow(SudokuCell cell) {
        return cells.get(cell.getRow());
    }

    public List<SudokuCell> getRow(int rowId) {
        for (List<SudokuCell> rows : this.cells) {
            if (rows.get(0).getRow() == rowId) {
                return rows;
            }
        }
        return null;
    }

    public List<SudokuCell> getCol(SudokuCell cell) {
        var col = new ArrayList<SudokuCell>();
        for (List<SudokuCell> row : cells) {
            col.add(row.get(cell.getCol()));
        }
        return col;
    }

    public List<SudokuCell> getCol(int colId) {
        var col = new ArrayList<SudokuCell>();
        for (List<SudokuCell> row : cells) {
            for (SudokuCell cell : row) {
                if (cell.getCol() == colId) {
                    col.add(cell);
                }
            }
        }
        return col;
    }

    public List<SudokuCell> getBox(SudokuCell cell) {
        var box = new ArrayList<SudokuCell>();
        for (List<SudokuCell> row : cells) {
            for (SudokuCell c : row) {
                if (cell.getBox() == c.getBox()) {
                    box.add(c);
                }
            }
        }
        return box;
    }

    public List<SudokuCell> getBox(int index, boolean isPopulated) {
        var box = new ArrayList<SudokuCell>();
        for (List<SudokuCell> row : cells) {
            for (SudokuCell c : row) {
                if (index == c.getBox() && c.isPopulated() == isPopulated) {
                    box.add(c);
                }
            }
        }
        return box;
    }

    public List<SudokuCell> getBoxAllCells(int index) {
        var box = new ArrayList<SudokuCell>();
        for (List<SudokuCell> row : cells) {
            for (SudokuCell c : row) {
                if (index == c.getBox()) {
                    box.add(c);
                }
            }
        }
        return box;
    }

    public List<List<SudokuCell>> getBoxes(boolean isPopulated) {
        var boxes = new ArrayList<List<SudokuCell>>();
        for (int i = 0; i < 9; i++) {
            boxes.add(this.getBox(i, isPopulated));
        }
        return boxes;
    }

    public List<List<SudokuCell>> getAllBoxes() {
        var boxes = new ArrayList<List<SudokuCell>>();
        for (int i = 0; i < 9; i++) {
            boxes.add(this.getBoxAllCells(i));
        }
        return boxes;
    }


    public boolean isSolved() {
        for (List<SudokuCell> row : cells) {
            for (SudokuCell cell : row) {
                if (!cell.isPopulated()) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SudokuGrid)) return false;
        SudokuGrid that = (SudokuGrid) o;
        return Objects.equals(cells, that.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer("Sudoku Grid: \n");
        s.append("+---+---+---+---+---+---+---+---+---+\n");
        for (List<SudokuCell> row : this.cells) {
            for (int i = 0; i < row.size(); i++) {
                if (i == 0) {
                    s.append("| ");
                }
                s.append(row.get(i).isPopulated() ? row.get(i).getAllocated() : " ").append(" | ");
            }
            s.append("\n");
            s.append("+---+---+---+---+---+---+---+---+---+\n");
        }
        return s.toString();
    }
}
