package works.hop.jdbc.s_8_validate_metadata;

import java.util.HashSet;
import java.util.Set;

public class TableInfo {

    final String tableName;
    final Set<TableColumn> tableColumns;
    final Set<TablePkColumn> tablePkColumns;
    final Set<TableFkColumn> tableFkColumns;

    public TableInfo(String tableName) {
        this(tableName, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public TableInfo(String tableName, Set<TableColumn> tableColumns, Set<TablePkColumn> tablePkColumns, Set<TableFkColumn> tableFkColumns) {
        this.tableName = tableName;
        this.tableColumns = tableColumns;
        this.tablePkColumns = tablePkColumns;
        this.tableFkColumns = tableFkColumns;
    }

    public void addColumn(TableColumn column) {
        if (tableColumns.stream().noneMatch(col -> col.columnName.equals(column.columnName))) {
            tableColumns.add(column);
        }
    }

    public void addPkColumn(TablePkColumn column) {
        if (tablePkColumns.stream().noneMatch(col -> col.columnName.equals(column.columnName))) {
            tablePkColumns.add(column);
        }
    }

    public void addColumn(TableFkColumn column) {
        if (tableFkColumns.stream().noneMatch(col -> col.pkColumnName.equals(column.pkColumnName) &&
                col.fkColumnName.equals(column.fkColumnName))) {
            tableFkColumns.add(column);
        }
    }

    @Override
    public String toString() {
        return "TableInfo{\n" +
                "\t\ttableName='" + tableName + "'\n" +
                "\t\ttableColumns=" + tableColumns + "\n" +
                "\t\ttablePkColumns=" + tablePkColumns + "\n" +
                "\t\ttableFkColumns=" + tableFkColumns + "\n" +
                "\t}";
    }

    public static TableInfoBuilder builder(){
        return new TableInfoBuilder();
    }
}
