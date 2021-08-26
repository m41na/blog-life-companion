package works.hop.jdbc.s_8_validate_metadata;

import java.util.HashSet;
import java.util.Set;

public class TableInfoBuilder {

    String tableName;
    Set<TableColumn> tableColumns = new HashSet<>();
    Set<TablePkColumn> tablePkColumns = new HashSet<>();
    Set<TableFkColumn> tableFkColumns = new HashSet<>();

    TableInfoBuilder(){}

    public TableInfoBuilder tableName(String tableName){
        this.tableName = tableName;
        return this;
    }

    public TableInfoBuilder tableColumn(String columnName, int ordinal){
        this.tableColumns.add(new TableColumn(columnName, ordinal));
        return this;
    }

    public TableInfoBuilder tablePkColumn(String columnName, int ordinal){
        this.tablePkColumns.add(new TablePkColumn(columnName, ordinal));
        return this;
    }

    public TableInfoBuilder tablePkColumn(String columnName, int keySeq, String pkName){
        this.tablePkColumns.add(new TablePkColumn(columnName, keySeq, pkName));
        return this;
    }

    public TableInfoBuilder tableFkColumn(String pkTableName, String pkColumnName, String fkTableName, String fkColumnName, int keySeq){
        this.tableFkColumns.add(new TableFkColumn(pkTableName, pkColumnName, fkTableName, fkColumnName, keySeq));
        return this;
    }

    public TableInfoBuilder tableFkColumn(String pkTableName, String pkColumnName, String pkName, String fkTableName, String fkColumnName, String fkName, int keySeq){
        this.tableFkColumns.add(new TableFkColumn(pkTableName, pkColumnName, pkName, fkTableName, fkColumnName, fkName, keySeq));
        return this;
    }

    public TableInfo build(){
        return new TableInfo(tableName, tableColumns, tablePkColumns, tableFkColumns);
    }
}
