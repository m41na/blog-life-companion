package works.hop.jdbc.s_8_validate_metadata;

public class TableColumn {

    final String columnName;
    final int ordinal;

    public TableColumn(String columnName, int ordinal) {
        this.columnName = columnName;
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        return "TableColumn{\n" +
                "\t\t\tcolumnName='" + columnName + "'\n" +
                "\t\t\tordinal=" + ordinal + "\n" +
                "\t\t}";
    }
}
