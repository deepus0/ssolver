package au.com.deepus.helper;

public enum SudokuEnum {

    ROW("Row"),
    COL("Column"),
    BOX("Box");

    private final String name;

    SudokuEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
