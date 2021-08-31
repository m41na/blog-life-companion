package works.hop.jdbc.i_6_insert_join_different_tables;

public class JoinTable {

    String tableName;
    String[] onColumns;
    String[] whereColumns;
    String[] inverseColumns;
    String[] compositeColumns;

    public JoinTable(String tableName, String[] compositeColumns, String[] inverseColumns, String[] onColumns, String[] whereColumns) {
        this.tableName = tableName;
        this.onColumns = onColumns;
        this.whereColumns = whereColumns;
        this.inverseColumns = inverseColumns;
        this.compositeColumns = compositeColumns;
    }

    public static JoinTableBuilder builder() {
        return new JoinTableBuilder();
    }
}
