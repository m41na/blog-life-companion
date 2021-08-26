package works.hop.jdbc.i_2_insert_embedded;

import works.hop.jdbc.i_0_insert.InsertOpException;

import java.sql.*;

public class Insert {

    private static final String connectionString = "jdbc:h2:./data/sample-i_1.db";

    public static void main(String[] args) {
        Task taskEntity = new Task("Crazy jacks", false);
        InsertResult<Task> task = insert("insert into tbl_task (name) values (?)", new String[]{"name"}, taskEntity);
        if (task.error != null) {
            System.out.println(task.error);
        } else {
            System.out.println(task.result);
        }
    }

    public static <T extends Entity> InsertResult<T> insert(String query, String[] columnNames, T entity) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                EntityMetadata metadata = entity.metadata();
                Object[] parameters = metadata.resolveQueryParameters(columnNames, entity);

                for (int i = 0; i < parameters.length; i++) {
                    ps.setObject(i + 1, parameters[i]);
                }

                int insertedRows = ps.executeUpdate();
                if(insertedRows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            String generatedColumnName = metadata.resolveGeneratedColumnName();
                            Class<?> generatedColumnType = metadata.resolveGeneratedColumnType(generatedColumnName);
                            entity.set(generatedColumnName, rs.getObject(generatedColumnName, generatedColumnType));
                        }
                        return InsertResult.success(entity);
                    } catch (Exception e) {
                        return InsertResult.failure(e);
                    }
                }
                else{
                    return InsertResult.failure(new InsertOpException("No new record was created"));
                }
            } catch (Exception e) {
                return InsertResult.failure(e);
            }
        } catch (Exception e) {
            return InsertResult.failure(e);
        }
    }
}
