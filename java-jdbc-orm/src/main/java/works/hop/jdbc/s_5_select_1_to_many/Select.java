package works.hop.jdbc.s_5_select_1_to_many;

import works.hop.jdbc.s_0_select.SelectResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Select {

    private static final String connectionString = "jdbc:h2:./data/sample-4.db";

    public static void main(String[] args) {
//        SelectResult<Task> tasks = select("select * from tbl_task", new Object[]{}, EntityRegistry.registry.get(Task.class));
        SelectResult<TaskV2> tasks = select("select * from tbl_task_v2", new Object[]{}, EntityRegistry.registry.get(TaskV2.class));
        if (tasks.error != null) {
            System.out.println(tasks.error);
        } else {
//            for (Task entity : tasks.result) {
            for (TaskV2 entity : tasks.result) {
                System.out.println(entity);
            }
        }
    }

    public static <T extends Entity> SelectResult<T> select(String query, Object[] parameters, EntityMetadata metadata) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            LocalCache<T> cache = new LocalCache<>();
            SelectResult<T> result = select(query, parameters, metadata, conn, cache);
            cache.clear();
            return result;
        } catch (Exception e) {
            return SelectResult.failure(e);
        }
    }

    private static <T extends Entity> SelectResult<T> select(String query, Object[] parameters, EntityMetadata metadata, Connection conn, LocalCache<T> cache) {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                Collection<T> collection = extractEntities(rs, metadata, conn, cache);
                return SelectResult.success(collection);
            } catch (Exception e) {
                return SelectResult.failure(e);
            }
        } catch (Exception e) {
            return SelectResult.failure(e);
        }
    }

    private static <T extends Entity> Collection<T> extractEntities(ResultSet rs, EntityMetadata metadata, Connection conn, LocalCache<T> cache) throws SQLException {
        Collection<T> collection = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            T data = extractEntity(rs, meta, metadata, conn, cache);
            collection.add(data);
        }
        return collection;
    }

    private static <T extends Entity> T extractEntity(ResultSet rs, ResultSetMetaData meta, EntityMetadata metadata, Connection conn, LocalCache<T> cache) throws SQLException {
        T data = metadata.entityInstance();
        int numberOfColumns = meta.getColumnCount();
        //check primary key column (either single column or composite columns)
        if (metadata.containsCompositePk()) {
            ColumnInfo compositePkColumnInfo = metadata.compositePkColumn();
            EntityMetadata compositePkEntityMetadata = EntityRegistry.registry.get(compositePkColumnInfo.attributeType);
            Object compositeId = extractEntity(rs, meta, compositePkEntityMetadata, conn, cache);
            Optional<T> cached = cache.getIfExists((Comparable<?>) compositeId);
            if (cached.isPresent()) {
                return cached.get();
            } else {
                cache.addIfNotExists((Comparable<?>) compositeId, data);
                //set attribute value in entity
                String attributeName = compositePkColumnInfo.attributeName;
                data.set(attributeName, compositeId);
            }
        }

        if (metadata.containsPkColumn()) {
            ColumnInfo pkColumnInfo = metadata.pkColumn();
            Object id = rs.getObject(pkColumnInfo.columnName, pkColumnInfo.attributeType);
            Optional<T> cached = cache.getIfExists((Comparable<?>) id);
            if (cached.isPresent()) {
                return cached.get();
            } else {
                cache.addIfNotExists((Comparable<?>) id, data);
            }
        }

        //check mapped columns
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = meta.getColumnName(i).toLowerCase(); //important since the name might be in upper case
            Optional<ColumnInfo> columnInfoOption = metadata.resolveByColumnName(columnName);
            if (columnInfoOption.isPresent()) {
                ColumnInfo columnInfo = columnInfoOption.get();
                if (!columnInfo.isFkColumn) {
                    String attributeName = columnInfo.attributeName;
                    Class<?> attributeType = columnInfo.isEnum ? String.class : columnInfo.attributeType;
                    data.set(attributeName, rs.getObject(i, attributeType));
                }
            }
        }

        //check for Embedded columns
        if (metadata.containsEmbedded()) {
            for (ColumnInfo embeddedColumnInfo : metadata.embeddedColumns()) {
                String attributeName = embeddedColumnInfo.attributeName;
                EntityMetadata embeddedEntityMetadata = EntityRegistry.registry.get(embeddedColumnInfo.attributeType);
                Object embeddedValue = extractEntity(rs, meta, embeddedEntityMetadata, conn, cache);
                data.set(attributeName, embeddedValue);
            }
        }

        //check for foreign key columns (with join having either single fk column or composite fk column)
        if (metadata.containsFkColumns()) {
            for (ColumnInfo fkColumnInfo : metadata.fkColumns()) {
                String attributeName = fkColumnInfo.attributeName;
                EntityMetadata joinedEntityMetadata = EntityRegistry.registry.get(fkColumnInfo.attributeType);
                if (fkColumnInfo.columnName != null) {
                    String query = joinedEntityMetadata.createJoinQuery(metadata.tableName, fkColumnInfo.columnName);
                    Object joinValue = rs.getObject(fkColumnInfo.columnName, joinedEntityMetadata.pkColumn().attributeType);
                    if (joinValue != null) {
                        SelectResult<?> fkEntityValue = select(query, new Object[]{joinValue}, joinedEntityMetadata, conn, cache);
                        if (fkEntityValue.result != null && fkEntityValue.result.size() > 0) {
                            data.set(attributeName, new ArrayList<>(fkEntityValue.result).get(0));
                        }
                    }
                } else {
                    //must be a composite fk column
                    System.out.println("Skipping composite fk column for now");
                    String[][] compositeColumns = fkColumnInfo.compositeColumns;
                    String query = joinedEntityMetadata.createCompositeJoinQuery(metadata.tableName, compositeColumns);
                    Object[] params = new Object[compositeColumns.length];
                    for (int i = 0; i < params.length; i++) {
                        String column = compositeColumns[i][0];
                        params[i] = rs.getObject(column);
                    }
                    SelectResult<?> compositeFkEntityValue = select(query, params, joinedEntityMetadata, conn, cache);
                    if (compositeFkEntityValue.result != null && compositeFkEntityValue.result.size() > 0) {
                        data.set(attributeName, new ArrayList<>(compositeFkEntityValue.result).get(0));
                    }
                }
            }
        }

        return data;
    }
}
