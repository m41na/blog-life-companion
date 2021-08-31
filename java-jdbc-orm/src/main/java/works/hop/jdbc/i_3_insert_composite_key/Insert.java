package works.hop.jdbc.i_3_insert_composite_key;

import works.hop.jdbc.i_0_insert.InsertOpException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Insert {

    private static final String connectionString = "jdbc:h2:./data/sample-i_3.db";

    public static void main(String[] args) {
        UserId userId = new UserId("some5@email.com", "poozi");
        Address address = new Address("chicago", "IL", "60606");
        User userEntity = new User(userId, "nicki", AccessLevel.USER, address, LocalDate.now());
        InsertResult<User> user = insert(userEntity);
        if (user.error != null) {
            System.out.println(user.error);
        } else {
            System.out.println(user.result);
        }
    }

    public static <T extends Entity> InsertResult<T> insert(T entity) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            EntityMetadata metadata = entity.metadata();
            Map<String, Object> insertableColumns = new LinkedHashMap<>(); //be sure to use "linked" version of hashmap to retain order
            resolveInsertableColumns(entity, insertableColumns);
            String[] columnNames = insertableColumns.keySet().toArray(new String[0]);
            String insertQuery = metadata.resolveInsertQuery(columnNames);

            try (PreparedStatement ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                Object[] parameters = insertableColumns.values().toArray(new Object[0]);

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
            .filter(info -> info.columnName != null)
            .forEach(info -> {
                if(info.isEnum){
                    if(info.isEnumOrdinal) {
                        map.put(info.columnName, ((Enum<?>) entity.get(info.attributeName)).ordinal());
                    }
                    else{
                        map.put(info.columnName, entity.get(info.attributeName).toString());
                    }
                }
                else {
                    map.put(info.columnName, entity.get(info.attributeName));
                }
            });
    }
}