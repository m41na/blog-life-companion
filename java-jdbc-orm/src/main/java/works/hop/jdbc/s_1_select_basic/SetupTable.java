package works.hop.jdbc.s_1_select_basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupTable {

    private static final String connectionString = "jdbc:h2:./data/sample-1.db";

    public static void createTable() {
        String sql = "create table if not exists tbl_task (\n" +
                "  id UUID default random_uuid(),\n" +
                "  name varchar(50) not null,\n" +
                "  done boolean not null default false,\n" +
                "  task_created timestamp not null default now(),\n" +
                "  constraint task_pk primary key(id),\n" +
                "  constraint uniq_name unique (name)\n" +
                ")";
        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void initializeData(String name) {
        String sql = "insert into tbl_task (name) values ('" + name + "')";
        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
//        connect();
//        createTaskTable();
        initializeData("Wake up");
        initializeData("Shower");
        initializeData("Eat Breakfast");
    }
}
