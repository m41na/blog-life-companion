package works.hop.jdbc.s_8_validate_metadata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcMetadata {

    private static final Logger logger = LogManager.getLogger(JdbcMetadata.class);
    private static final String connectionString = "jdbc:h2:./data/sample-8.db";

    public static void main(String[] args) {
        JdbcTablesInfo meta = extractTableInfo();
        System.out.println(meta);
        validateTableCount(meta);
        validateColumnsInRegistryTables(meta);
    }

    public static JdbcTablesInfo extractTableInfo() {
        JdbcTablesInfo meta = new JdbcTablesInfo();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            try (ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"})) {
                while (resultSet.next()) {
                    TableInfoBuilder builder = TableInfo.builder();
                    String tableName = resultSet.getString("TABLE_NAME");
                    builder.tableName(tableName);
                    extractColumnInfo(tableName, databaseMetaData, builder);
                    extractPkColumnInfo(tableName, databaseMetaData, builder);
                    extractFkColumnInfo(tableName, databaseMetaData, builder);
                    meta.addTableInfo(builder.build());
                }
            }
            return meta;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void extractFkColumnInfo(String tableName, DatabaseMetaData databaseMetaData, TableInfoBuilder builder) {
        try (ResultSet columns = databaseMetaData.getImportedKeys(null, null, tableName)) {
            while (columns.next()) {
                String pkTableName = columns.getString("PKTABLE_NAME");
                String pkColumnName = columns.getString("PKCOLUMN_NAME");
                String pkName = columns.getString("PK_NAME");
                String fkTableName = columns.getString("FKTABLE_NAME");
                String fkColumnName = columns.getString("FKCOLUMN_NAME");
                String fkName = columns.getString("FK_NAME");
                String keySeq = columns.getString("KEY_SEQ");
                builder.tableFkColumn(pkTableName, pkColumnName, pkName, fkTableName, fkColumnName, fkName, Integer.parseInt(keySeq));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private static void extractPkColumnInfo(String tableName, DatabaseMetaData databaseMetaData, TableInfoBuilder builder) {
        try (ResultSet columns = databaseMetaData.getPrimaryKeys(null, null, tableName)) {
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String pkName = columns.getString("PK_NAME");
                String keySeq = columns.getString("KEY_SEQ");
                builder.tablePkColumn(columnName, Integer.parseInt(keySeq), pkName);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private static void extractColumnInfo(String tableName, DatabaseMetaData databaseMetaData, TableInfoBuilder builder) {
        try (ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null)) {
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String ordinalPos = columns.getString("ORDINAL_POSITION");
                builder.tableColumn(columnName, Integer.parseInt(ordinalPos));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static void validateTableCount(JdbcTablesInfo meta) {
        List<EntityMetadata> registryTables = registryEntities();
        String[] schemaTables = meta.schemaTablesNames();
        if (registryTables.size() != schemaTables.length) {
            String error = String.format("Expected number of tables is the registry (%d) to match " +
                    "the number of tables in the schema (%d)", registryTables.size(), schemaTables.length);
            logger.error("Table count: {}", error);
        } else {
            logger.info("Table count: {}", "OK");
        }
    }

    private static List<EntityMetadata> registryEntities() {
        return EntityRegistry.registry.values().stream().filter(entityMetadata ->
                entityMetadata.entityType == EntityType.IDENTIFIABLE).collect(Collectors.toList());
    }

    public static void validateColumnsInRegistryTables(JdbcTablesInfo meta) {
        Collection<EntityMetadata> tables = EntityRegistry.registry.values();
        for (EntityMetadata entityMetadata : tables) {
            Optional<TableInfo> tableInfo = meta.tableInfo(entityMetadata.tableName);
            tableInfo.ifPresent(info -> {
                validateColumnsInRegistryTable(info);
                validatePkColumnInRegistryTable(info);
                validateFkColumnInRegistryTable(info);
            });
        }
    }

    private static void validateFkColumnInRegistryTable(TableInfo tableInfo) {
        List<ColumnInfo> entityFkColumns = getEntityFkColumnsByTableName(tableInfo.tableName);
        if (entityFkColumns.size() != tableInfo.tableFkColumns.size()) {
            logger.error("{}: Expected entity fk columns ({}) to match schema table fk columns ({})",
                   tableInfo.tableName, entityFkColumns.size(), tableInfo.tableFkColumns.size());
        }

        for (TableFkColumn fkColumn : tableInfo.tableFkColumns) {
            boolean missing = true;
            for (ColumnInfo columnInfo : entityFkColumns) {
                if (fkColumn.fkColumnName.equalsIgnoreCase(columnInfo.columnName)) {
                    missing = false;
                    break;
                }
            }

            if (missing) {
                logger.error("{}: Expected to find {} column in entity fk columns", tableInfo.tableName, fkColumn.fkColumnName);
            }
        }
    }

    private static Optional<EntityMetadata> getEntityByTableName(String tableName) {
        return EntityRegistry.registry.values().stream().filter(entity -> tableName.equalsIgnoreCase(entity.tableName)).findFirst();
    }

    private static List<ColumnInfo> getEntityColumnsByTableName(String tableName) {
        return getEntityByTableName(tableName).map(entity -> entity.columns).orElse(Collections.emptyList());
    }

    private static Optional<ColumnInfo> getEntityPkColumnByTableName(String tableName) {
        return getEntityByTableName(tableName).map(EntityMetadata::pkColumn);
    }

    private static List<ColumnInfo> getEntityFkColumnsByTableName(String tableName) {
        return getEntityByTableName(tableName).map(EntityMetadata::fkColumns).orElse(Collections.emptyList());
    }

    private static void validatePkColumnInRegistryTable(TableInfo tableInfo) {
        System.out.println("Still working on it");
    }

    private static void validateColumnsInRegistryTable(TableInfo tableInfo) {
        List<ColumnInfo> entityColumns = getEntityColumnsByTableName(tableInfo.tableName);
        if (entityColumns.size() != tableInfo.tableColumns.size()) {
            logger.error("{}: Expected entity columns ({}) to match schema table columns ({})",
                    tableInfo.tableName, entityColumns.size(), tableInfo.tableColumns.size());
        }

        for (TableColumn tableColumn : tableInfo.tableColumns) {
            Optional<ColumnInfo> columnOption = entityColumns.stream().filter(column ->
                    tableColumn.columnName.equalsIgnoreCase(column.columnName)).findFirst();
            if (columnOption.isEmpty()) {
                logger.error("{}: Expected {} column to exists in the entity metadata", tableInfo.tableName, tableColumn.columnName);
            }
        }
    }
}
