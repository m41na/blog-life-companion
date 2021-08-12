package works.hop.jdbc.s_4_select_1_to_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupTable {

    private static final String connectionString = "jdbc:h2:./data/sample-4.db";

    public static void createTable() {
        String[] queries = new String[]{
                "drop table if exists tbl_task;",
                "create table if not exists tbl_task (\n" +
                "  id UUID default random_uuid(),\n" +
                "  name varchar(50) not null,\n" +
                "  done boolean not null default false,\n" +
                "  task_created timestamp not null default now(),\n" +
                "  parent_task UUID,\n" +
                "  constraint task_pk primary key(id),\n" +
                "  constraint uniq_name unique (name),\n" +
                "  constraint task_parent_fk foreign key(parent_task) references tbl_task(id)\n" +
                ");"
        };
        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            for(String sql : queries) {
                stmt.execute(sql);
            }
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
        createTable();
        initializeData("Wake up");
        initializeData("Shower");
        initializeData("Eat Breakfast");
    }
}
