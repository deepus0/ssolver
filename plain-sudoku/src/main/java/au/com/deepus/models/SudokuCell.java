package au.com.deepus.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SudokuCell {

    private int id;
    private int allocated;
    private List<Integer> possibilities;
    private int row;
    private int col;
    private int box;
    private int boxCell;

    public SudokuCell(int id, int allocated, int row, int col) {
        this.id = id;
        this.allocated = allocated;
        this.possibilities = new ArrayList<>();
        this.row = row;
        this.col = col;
        this.box = calculateBox(row, col);
        this.boxCell = calculateBoxCell();
    }

    public void setAllocated(int allocated) {
        this.allocated = allocated;
        this.setPossibilities(new ArrayList<>());
    }

    public SudokuCell() {
        this.allocated = 0;
    }

    public boolean isPopulated() {
        return allocated != 0;
    }

    private int calculateBox(int row, int col) {
        if (row <= 2 && col <= 2) {
            return 0;
        } else if (row <= 2 && col <= 5) {
            return 1;
        } else if (row <= 2) {
            return 2;
        } else if (row <= 5 && col <= 2) {
            return 3;
        } else if (row <= 5 && col <= 5) {
            return 4;
        } else if (row <= 5) {
            return 5;
        } else if (col <= 2) {
            return 6;
        } else if (col <= 5) {
            return 7;
        } else {
            return 8;
        }
    }

    private int calculateBoxCell() {
        return row * 3 + col % 3;
    }


}
