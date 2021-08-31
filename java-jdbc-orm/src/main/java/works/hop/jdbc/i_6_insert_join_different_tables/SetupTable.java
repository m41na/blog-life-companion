package works.hop.jdbc.i_6_insert_join_different_tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupTable {

    private static final String connectionString = "jdbc:h2:./data/sample-i_6.db";

    public static void createTables() {
        String[] queries = new String[]{
                "drop table if exists tbl_task;",
                "drop table if exists tbl_task_v2;",
                "drop table if exists tbl_user;",
                "drop table if exists tbl_user_v2;",
                "create table if not exists tbl_user (\n" +
                        "  id UUID default random_uuid(),\n" +
                        "  email_address varchar(100) not null,\n" +
                        "  nickname varchar(50),\n" +
                        "  date_joined timestamp not null default now(),\n" +
                        "  access_level varchar default 'VISITOR',\n" +
                        "  addr_city varchar(50),\n" +
                        "  addr_state varchar(30),\n" +
                        "  addr_postal_code varchar(10),\n" +
                        "  constraint customer_pk primary key(email_address, username)\n" +
                        ");",
                "create table if not exists tbl_user_v2 (\n" +
                        "  email_address varchar(100) not null,\n" +
                        "  username varchar(50) not null,\n" +
                        "  nickname varchar(50),\n" +
                        "  date_joined timestamp not null default now(),\n" +
                        "  access_level varchar default 'VISITOR',\n" +
                        "  addr_city varchar(50),\n" +
                        "  addr_state varchar(30),\n" +
                        "  addr_postal_code varchar(10),\n" +
                        "  constraint customer_pk primary key(email_address, username)\n" +
                        ");",
                "create table if not exists tbl_task (\n" +
                        "  id UUID default random_uuid(),\n" +
                        "  name varchar(50) not null,\n" +
                        "  done boolean not null default false,\n" +
                        "  task_created timestamp not null default now(),\n" +
                        "  parent_task UUID,\n" +
                        "  task_assignee UUID,\n" +
                        "  constraint task_pk primary key(id),\n" +
                        "  constraint uniq_name unique (name),\n" +
                        "  constraint task_parent_fk foreign key(parent_task) references tbl_task(id),\n" +
                        "  constraint task_assignee_fk foreign key(task_assignee) references tbl_user(id)\n" +
                        ");",
                "create table if not exists tbl_task_v2 (\n" +
                        "  num int NOT null,\n" +
                        "  name varchar(50) not null,\n" +
                        "  done boolean not null default false,\n" +
                        "  task_created timestamp not null default now(),\n" +
                        "  parent_task_num int,\n" +
                        "  parent_task_name varchar(50),\n" +
                        "  constraint task_pk_v2 primary key(num, name),\n" +
                        "  constraint task_parent_fk_v2 foreign key(parent_task_num, parent_task_name) references tbl_task_v2(num, name)\n" +
                        ");"
        };
        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            for (String sql : queries) {
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
        createTables();
        initializeData("Wake up");
        initializeData("Shower");
        initializeData("Eat Breakfast");
    }
}
