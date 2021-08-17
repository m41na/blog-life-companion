package works.hop.jdbc.s_7_select_refactoring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import works.hop.jdbc.s_0_select.SelectResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Select {

    private static final String connectionString = "jdbc:h2:./data/sample-7.db";
    private static final Logger logger = LogManager.getLogger(Select.class);

    public static void main(String[] args) {
        SelectResult<TaskV4> tasks = select("select * from tbl_task_v4", new Object[]{}, EntityRegistry.registry.get(TaskV4.class));
        if (tasks.error != null) {
            System.out.println(tasks.error);
        } else {
            for (TaskV4 entity : tasks.result) {
                System.out.println(entity);
            }
        }

        SelectResult<UserV4> users = select("select * from tbl_user_v4", new Object[]{}, EntityRegistry.registry.get(UserV4.class));
        if (users.error != null) {
            System.out.println(users.error);
        } else {
            for (UserV4 entity : users.result) {
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
            logger.error("select error", e);
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
                logger.error("select error", e);
                return SelectResult.failure(e);
            }
        } catch (Exception e) {
            logger.error("select error", e);
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
        //check composite primary key columns
        if (metadata.containsCompositePk()) {
            ColumnInfo compositePkColumnInfo = metadata.compositePkColumn();
            EntityMetadata compositePkEntityMetadata = EntityRegistry.registry.get(compositePkColumnInfo.attributeType);
            Object compositeId = extractEntity(rs, meta, compositePkEntityMetadata, conn, cache);
            Optional<T> cached = cache.getIfExists(compositeId);
            if (cached.isPresent()) {
                return cached.get();
            } else {
                cache.addIfNotExists(compositeId, data);
                //set attribute value in entity
                String attributeName = compositePkColumnInfo.attributeName;
                data.set(attributeName, compositeId);
            }
        }

        //check primary key column
        if (metadata.containsPkColumn()) {
            ColumnInfo pkColumnInfo = metadata.pkColumn();
            Object id = rs.getObject(pkColumnInfo.columnName, pkColumnInfo.attributeType);
            Optional<T> cached = cache.getIfExists(id);
            if (cached.isPresent()) {
                return cached.get();
            } else {
                cache.addIfNotExists(id, data);
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

        //check embedded columns
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
                    if(fkColumnInfo.inverseFkColumn != null){
                        String query = joinedEntityMetadata.createInverseJoinQuery(fkColumnInfo.inverseFkColumn, fkColumnInfo.columnName);
                        Object joinValue = rs.getObject(fkColumnInfo.inverseFkColumn, joinedEntityMetadata.pkColumn().attributeType);
                        if (joinValue != null) {
                            SelectResult<?> fkEntityValue = select(query, new Object[]{joinValue}, joinedEntityMetadata, conn, cache);
                            if (fkEntityValue.result != null && fkEntityValue.result.size() > 0) {
                                data.set(attributeName, new ArrayList<>(fkEntityValue.result).get(0));
                            }
                        }
                    }
                    else {
                        String query = joinedEntityMetadata.createJoinQuery(metadata.tableName, fkColumnInfo.columnName);
                        Object joinValue = rs.getObject(fkColumnInfo.columnName, joinedEntityMetadata.pkColumn().attributeType);
                        if (joinValue != null) {
                            SelectResult<?> fkEntityValue = select(query, new Object[]{joinValue}, joinedEntityMetadata, conn, cache);
                            if (fkEntityValue.result != null && fkEntityValue.result.size() > 0) {
                                data.set(attributeName, new ArrayList<>(fkEntityValue.result).get(0));
                            }
                        }
                    }
                } else {
                    //must be a composite fk column
                    if(fkColumnInfo.isInverseComposite){
                        String[][] compositeColumns = fkColumnInfo.compositeColumns;
                        String query = joinedEntityMetadata.createInverseCompositeJoinQuery(metadata.tableName, compositeColumns);
                        Object[] params = new Object[compositeColumns.length];
                        for (int i = 0; i < params.length; i++) {
                            String column = compositeColumns[i][1];
                            params[i] = rs.getObject(column);
                        }
                        SelectResult<?> compositeFkEntityValue = select(query, params, joinedEntityMetadata, conn, cache);
                        if (compositeFkEntityValue.result != null && compositeFkEntityValue.result.size() > 0) {
                            data.set(attributeName, new ArrayList<>(compositeFkEntityValue.result).get(0));
                        }
                    }
                    else {
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
        }

        //check for Collection columns
        if (metadata.containsCollectionColumns()) {
            for (ColumnInfo fkColumnInfo : metadata.collectionColumns()) {
                String attributeName = fkColumnInfo.attributeName;
                EntityMetadata joinedEntityMetadata = EntityRegistry.registry.get(fkColumnInfo.attributeType);
                if(fkColumnInfo.joinTable != null){
                    JoinTable joinTable = fkColumnInfo.joinTable;
                    if(joinTable.compositeColumns != null) {
                        String query = joinedEntityMetadata.createJoinTableCompositeJoinQuery(joinTable);
                        Object[] params = new Object[joinTable.compositeColumns.length];
                        for (int i = 0; i < params.length; i++) {
                            String column = joinTable.compositeColumns[i];
                            params[i] = rs.getObject(column);
                        }
                        SelectResult<?> compositeFkEntityValue = select(query, params, joinedEntityMetadata, conn, cache);
                        if (compositeFkEntityValue.result != null) {
                            data.set(attributeName, compositeFkEntityValue.result);
                        }
                    }
                    else{
                        String query = joinedEntityMetadata.createJoinTableJoinQuery(joinTable);
                        Object joinValue = rs.getObject(joinTable.inverseColumns[0], joinedEntityMetadata.pkColumn().attributeType);
                        if (joinValue != null) {
                            SelectResult<?> fkEntityValue = select(query, new Object[]{joinValue}, joinedEntityMetadata, conn, cache);
                            if (fkEntityValue.result != null) {
                                data.set(attributeName, fkEntityValue.result);
                            }
                        }
                    }
                }
                else if (fkColumnInfo.columnName != null) {
                    if(fkColumnInfo.inverseFkColumn != null){
                        String query = joinedEntityMetadata.createInverseJoinQuery(metadata.tableName, fkColumnInfo.columnName);
                        Object joinValue = rs.getObject(fkColumnInfo.inverseFkColumn, joinedEntityMetadata.pkColumn().attributeType);
                        if (joinValue != null) {
                            SelectResult<?> fkEntityValue = select(query, new Object[]{joinValue}, joinedEntityMetadata, conn, cache);
                            if (fkEntityValue.result != null) {
                                data.set(attributeName, fkEntityValue.result);
                            }
                        }
                    }
                    else {
                        String query = joinedEntityMetadata.createJoinQuery(metadata.tableName, fkColumnInfo.columnName);
                        Object joinValue = rs.getObject(fkColumnInfo.columnName, joinedEntityMetadata.pkColumn().attributeType);
                        if (joinValue != null) {
                            SelectResult<?> fkEntityValue = select(query, new Object[]{joinValue}, joinedEntityMetadata, conn, cache);
                            if (fkEntityValue.result != null) {
                                data.set(attributeName, fkEntityValue.result);
                            }
                        }
                    }
                } else {
                    //must be a composite fk column
                    if(fkColumnInfo.isInverseComposite){
                        String[][] compositeColumns = fkColumnInfo.compositeColumns;
                        String query = joinedEntityMetadata.createInverseCompositeJoinQuery(metadata.tableName, compositeColumns);
                        Object[] params = new Object[compositeColumns.length];
                        for (int i = 0; i < params.length; i++) {
                            String column = compositeColumns[i][1];
                            params[i] = rs.getObject(column);
                        }
                        SelectResult<?> compositeFkEntityValue = select(query, params, joinedEntityMetadata, conn, cache);
                        if (compositeFkEntityValue.result != null) {
                            data.set(attributeName, compositeFkEntityValue.result);
                        }
                    }
                    else {
                        String[][] compositeColumns = fkColumnInfo.compositeColumns;
                        String query = joinedEntityMetadata.createCompositeJoinQuery(metadata.tableName, compositeColumns);
                        Object[] params = new Object[compositeColumns.length];
                        for (int i = 0; i < params.length; i++) {
                            String column = compositeColumns[i][0];
                            params[i] = rs.getObject(column);
                        }
                        SelectResult<?> compositeFkEntityValue = select(query, params, joinedEntityMetadata, conn, cache);
                        if (compositeFkEntityValue.result != null) {
                            data.set(attributeName, compositeFkEntityValue.result);
                        }
                    }
                }
            }
        }
        return data;
    }
}
