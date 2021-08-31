package works.hop.jdbc.i_4_insert_1_to_1;

import works.hop.jdbc.i_0_insert.InsertOpException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Insert {

    private static final String connectionString = "jdbc:h2:./data/sample-i_4.db";

    public static void main(String[] args) {
//        Task task = new Task(null, "soma-tenor", false, LocalDate.now(),
//                new Task(null, "soma", false, LocalDate.now(), null));
//        InsertResult<Task> taskResult = insert(task);
//        if (taskResult.error != null) {
//            System.out.println(taskResult.error);
//        } else {
//            System.out.println(taskResult.result);
//        }

        TaskId somaTaskId = new TaskId(1, "soma");
        TaskId tenorTaskId = new TaskId(2, "soma-tenor");
        TaskV2 task = new TaskV2(tenorTaskId, false, LocalDate.now(),
                new TaskV2(somaTaskId, false, LocalDate.now(), null));
        InsertResult<TaskV2> taskResult = insert(task);
        if (taskResult.error != null) {
            System.out.println(taskResult.error);
        } else {
            System.out.println(taskResult.result);
        }
    }

    public static <T extends Entity> InsertResult<T> insert(T entity) {
        try {
            Connection conn = DriverManager.getConnection(connectionString);
            conn.setAutoCommit(false);
            try {
                InsertResult<T> result = insert(entity, conn);
                conn.commit();
                return result;
            } catch (Exception e) {
                conn.rollback();
                return InsertResult.failure(e);
            } finally {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            throw new InsertOpException("Could not get connection to insert data record");
        }
    }

    public static <T extends Entity> InsertResult<T> insert(T entity, Connection conn) {
        EntityMetadata metadata = entity.metadata();
        Map<String, Object> insertableColumns = new LinkedHashMap<>(); //be sure to use "linked" version of hashmap to retain order

        //handle foreign key associations first
        if (metadata.containsFkColumns()) {
            for (ColumnInfo fkColumnInfo : metadata.fkColumns()) {
                Entity fkEntity = (Entity) entity.get(fkColumnInfo.attributeName);
                if (fkColumnInfo.compositeColumns.length == 0) {
                    if (fkEntity != null) {
                        InsertResult<Entity> insertResult = insert(fkEntity, conn);
                        if (insertResult.error != null) {
                            throw new InsertOpException(insertResult.error);
                        }
                        EntityMetadata fkEntityMetadata = fkEntity.metadata();
                        if (!fkEntityMetadata.containsPkColumn()) {
                            throw new InsertOpException(String.format("Expected %s entity to contain a primary key mapping", fkEntityMetadata.tableName));
                        }
                        ColumnInfo columnInfo = fkEntityMetadata.pkColumn();
                        insertableColumns.put(fkColumnInfo.columnName, insertResult.result.get(columnInfo.attributeName));
                    }
                } else {
                    if (fkEntity != null) {
                        InsertResult<Entity> insertResult = insert(fkEntity, conn);
                        if (insertResult.error != null) {
                            throw new InsertOpException(insertResult.error);
                        }
                        EntityMetadata fkEntityMetadata = fkEntity.metadata();
                        if (!fkEntityMetadata.containsCompositePk()) {
                            throw new InsertOpException(String.format("Expected %s entity to contain a composite primary key mapping", fkEntityMetadata.tableName));
                        }
                        ColumnInfo columnInfo = fkEntityMetadata.compositePkColumn();
                        String[][] compositeColumns = fkColumnInfo.compositeColumns;
                        for (int i = 0; i < compositeColumns.length; i++) {
                            String fkColumnName = compositeColumns[i][0];
                            String targetColumn = compositeColumns[i][1];
                            ColumnInfo column = EntityRegistry.registry.get(columnInfo.attributeType).columns.stream()
                                    .filter(info -> info.columnName.equals(targetColumn))
                                    .findFirst()
                                    .orElseThrow(() -> new InsertOpException(String.format("Expected to find composite column mapping for %s", targetColumn)));
                            insertableColumns.put(fkColumnName, ((Entity) insertResult.result.get(columnInfo.attributeName)).get(column.attributeName));
                        }
                    }
                }
            }
        }

        //proceed as before
        resolveInsertableColumns(entity, insertableColumns);
        String[] columnNames = insertableColumns.keySet().stream()
                .filter(key -> insertableColumns.get(key) != null).toArray(String[]::new);
        String insertQuery = metadata.createInsertQuery(columnNames);

        try (PreparedStatement ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            Object[] parameters = insertableColumns.values().stream()
                    .filter(Objects::nonNull).toArray(Object[]::new);

            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }

            int insertedRows = ps.executeUpdate();
            if (insertedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        if (metadata.containsGenerated()) {
                            ColumnInfo generatedColumn = metadata.generatedColumn();
                            Class<?> generatedColumnType = generatedColumn.attributeType;
                            entity.set(generatedColumn.attributeName, rs.getObject(generatedColumn.columnName, generatedColumnType));
                        }
                    }
                    return InsertResult.success(entity);
                } catch (Exception e) {
                    return InsertResult.failure(e);
                }
            } else {
                return InsertResult.failure(new InsertOpException("No new record was created"));
            }
        } catch (Exception e) {
            return InsertResult.failure(e);
        }
    }

    private static void resolveInsertableColumns(Entity entity, Map<String, Object> map) {
        EntityMetadata entityMetadata = entity.metadata();
        if (entityMetadata.containsEmbedded()) {
            for (ColumnInfo embeddedColumn : entityMetadata.embeddedColumns()) {
                Entity embeddedValue = (Entity) entity.get(embeddedColumn.attributeName);
                resolveInsertableColumns(embeddedValue, map);
            }
        }
        if (entityMetadata.containsCompositePk()) {
            ColumnInfo compositeKeyColumn = entityMetadata.compositePkColumn();
            Entity compositeKey = (Entity) entity.get(compositeKeyColumn.attributeName);
            resolveInsertableColumns(compositeKey, map);
        }

        entityMetadata.columns.stream()
                .filter(info -> !info.isAutoGenerated)
                .filter(info -> !info.isFkColumn)
                .filter(info -> info.columnName != null)
                .forEach(info -> {
                    if (info.isEnum) {
                        if (info.isEnumOrdinal) {
                            map.put(info.columnName, ((Enum<?>) entity.get(info.attributeName)).ordinal());
                        } else {
                            map.put(info.columnName, entity.get(info.attributeName).toString());
                        }
                    } else {
                        map.put(info.columnName, entity.get(info.attributeName));
                    }
                });
    }
}
