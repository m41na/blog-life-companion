package works.hop.jdbc.s_8_validate_metadata;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class JdbcTablesInfo {

    private final Set<TableInfo> tablesInfo = new HashSet<>();

    public void addTableInfo(TableInfo tableInfo) {
        if (tablesInfo.stream().noneMatch(table -> table.tableName.equals(tableInfo.tableName))) {
            tablesInfo.add(tableInfo);
        }
    }

    public String[] schemaTablesNames(){
        return this.tablesInfo.stream().map(table -> table.tableName).toArray(String[]::new);
    }

    public Optional<TableInfo> tableInfo(String tableName){
        return tablesInfo.stream().filter(tableInfo -> tableInfo.tableName.equalsIgnoreCase(tableName)).findFirst();
    }

    public void forEach(Consumer<TableInfo> consumer){
        tablesInfo.forEach(consumer);
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder("JdbcTablesInfo{\n");
        for (TableInfo table : tablesInfo) {
            value.append("\ttablesInfo=").append(table).append("\n");
        }
        return value.append("}").toString();
    }
}
