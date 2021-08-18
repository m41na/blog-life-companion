package works.hop.jdbc.s_8_validate_metadata;

import java.sql.*;

public class JdbcMetadata {

    private static final String connectionString = "jdbc:h2:./data/sample-7.db";

    public static void extractTableInfo(String name) {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            try (ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"})) {
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    extractColumnInfo(tableName, databaseMetaData);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void extractColumnInfo(String tableName, DatabaseMetaData databaseMetaData) {
        try (ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null)) {
            while (columns.next()){
                //TODO: start validating here
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
