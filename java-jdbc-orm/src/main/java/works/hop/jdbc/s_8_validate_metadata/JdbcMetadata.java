package works.hop.jdbc.s_8_validate_metadata;

import org.h2.table.TableBase;

import java.sql.*;

public class JdbcMetadata {

    private static final String connectionString = "jdbc:h2:./data/sample-8.db";

    public static void main(String[] args) {
        extractTableInfo();
    }

    public static void extractTableInfo() {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            try (ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"})) {
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    System.out.println("------------- " + tableName + " --------------");
                    extractColumnInfo(tableName, databaseMetaData);
                    System.out.println("--------------- PK Columns -------------------");
                    extractPkColumnInfo(tableName, databaseMetaData);
                    System.out.println("--------------- FK Columns -------------------");
                    extractFkColumnInfo(tableName, databaseMetaData);
                    System.out.println("----------------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void extractFkColumnInfo(String tableName, DatabaseMetaData databaseMetaData) {
        try (ResultSet columns = databaseMetaData.getImportedKeys(null,null, tableName)) {
            while (columns.next()){
                //TODO: start validating here
                System.out.println(columns.getString("PKTABLE_NAME"));
                System.out.println(columns.getString("PKCOLUMN_NAME"));
                System.out.println(columns.getString("FKTABLE_NAME"));
                System.out.println(columns.getString("FKCOLUMN_NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void extractPkColumnInfo(String tableName, DatabaseMetaData databaseMetaData) {
        try (ResultSet columns = databaseMetaData.getPrimaryKeys(null,null, tableName)) {
            while (columns.next()){
                //TODO: start validating here
                System.out.println(columns.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void extractColumnInfo(String tableName, DatabaseMetaData databaseMetaData) {
        try (ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null)) {
            while (columns.next()){
                //TODO: start validating here
                System.out.println(columns.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
