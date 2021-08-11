package works.hop.jdbc.s_0_select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckConnection {

    private static final String connectionString = "jdbc:h2:./data/sample-1.db";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
