package works.hop.jdbc.s_7_select_refactoring;

public class JoinTable {

    String tableName;
    String[] onColumns;
    String[] whereColumns;
    String[] inverseColumns;
    String[] compositeColumns;
    String[] limitColumns;

    public JoinTable(String tableName, String[] compositeColumns, String[] inverseColumns, String[] onColumns, String[] whereColumns, String[] limitColumns) {
        this.tableName = tableName;
        this.onColumns = onColumns;
        this.whereColumns = whereColumns;
        this.inverseColumns = inverseColumns;
        this.compositeColumns = compositeColumns;
        this.limitColumns = limitColumns;
    }

    public static JoinTableBuilder builder() {
        return new JoinTableBuilder();
    }
}
