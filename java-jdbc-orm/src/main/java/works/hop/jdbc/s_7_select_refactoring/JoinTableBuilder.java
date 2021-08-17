package works.hop.jdbc.s_7_select_refactoring;

public class JoinTableBuilder {

    private String tableName;
    private String[] onColumns = {};
    private String[] whereColumns = {};
    private String[] inverseColumns = {};
    private String[] compositeColumns = {};

    public JoinTableBuilder tableName(String tableName){
        this.tableName = tableName;
        return this;
    }

    public JoinTableBuilder onColumn(String onColumn){
        String[] newArray = new String[onColumns.length + 1];
        System.arraycopy(this.onColumns, 0, newArray, 0, onColumns.length);
        this.onColumns = newArray;
        this.onColumns[onColumns.length - 1] = onColumn;
        return this;
    }

    public JoinTableBuilder onColumns(String... onColumns){
        this.onColumns = onColumns;
        return this;
    }

    public JoinTableBuilder whereColumn(String whereColumn){
        String[] newArray = new String[whereColumns.length + 1];
        System.arraycopy(this.whereColumns, 0, newArray, 0, whereColumns.length);
        this.whereColumns = newArray;
        this.whereColumns[whereColumns.length - 1] = whereColumn;
        return this;
    }

    public JoinTableBuilder whereColumns(String... whereColumns){
        this.whereColumns = whereColumns;
        return this;
    }

    public JoinTableBuilder inverseColumn(String inverseColumn) {
        String[] newArray = new String[inverseColumns.length + 1];
        System.arraycopy(this.inverseColumns, 0, newArray, 0, inverseColumns.length);
        this.inverseColumns = newArray;
        this.inverseColumns[inverseColumns.length - 1] = inverseColumn;
        return this;
    }

    public JoinTableBuilder inverseColumns(String... inverseColumns){
        this.inverseColumns = inverseColumns;
        return this;
    }

    public JoinTableBuilder compositeColumns(String... compositeColumns) {
        this.compositeColumns = compositeColumns;
        return this;
    }

    public JoinTable build() {
        return new JoinTable(tableName, compositeColumns, inverseColumns, onColumns, whereColumns);
    }
}
