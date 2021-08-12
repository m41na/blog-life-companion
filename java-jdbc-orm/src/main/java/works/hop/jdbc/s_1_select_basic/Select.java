package works.hop.jdbc.s_1_select_basic;

import works.hop.jdbc.s_0_select.SelectResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Select {

    private static final String connectionString = "jdbc:h2:./data/sample-1.db";

    public static void main(String[] args) {
        SelectResult<Task> tasks = select("select * from tbl_task", new Object[]{}, Task.metadata);
        if (tasks.error != null) {
            System.out.println(tasks.error);
        } else {
            for (Task task : tasks.result) {
                System.out.println(task);
            }
        }
    }

    public static <T extends Entity> SelectResult<T> select(String query, Object[] parameters, EntityMetadata metadata) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setObject(i + 1, parameters[i]);
                }

                Collection<T> collection = new ArrayList<>();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        T data = metadata.entityInstance();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int numberOfColumns = rsmd.getColumnCount();
                        for (int i = 1; i <= numberOfColumns; i++) {
                            String attributeName = metadata.resolveAttributeName(rsmd.getColumnName(i).toLowerCase());
                            Class<?> attributeType = metadata.resolveAttributeType(attributeName);
                            data.set(attributeName, rs.getObject(i, attributeType));
                        }
                        collection.add(data);
                    }
                    return SelectResult.success(collection);
                } catch (Exception e) {
                    return SelectResult.failure(e);
                }
            } catch (Exception e) {
                return SelectResult.failure(e);
            }
        } catch (Exception e) {
            return SelectResult.failure(e);
        }
    }
}
