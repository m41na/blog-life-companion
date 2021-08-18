package works.hop.jdbc.s_7_select_refactoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupTable {

    private static final String connectionString = "jdbc:h2:./data/sample-7.db";

    public static void createTable() {
        String[] queries = new String[]{
                "drop table if exists tbl_user_task_v4;\n" +
                        "drop table if exists tbl_task_v4;\n" +
                        "drop table if exists tbl_user_v4;\n" +
                        "\n" +
                        "create table if not exists tbl_user_v4 (\n" +
                        "  email_address varchar(100) not null,\n" +
                        "  username varchar(50) not null,\n" +
                        "  nickname varchar(50),\n" +
                        "  date_joined timestamp not null default now(),\n" +
                        "  access_level varchar default 'VISITOR',\n" +
                        "  addr_city varchar(50),\n" +
                        "  addr_state varchar(30),\n" +
                        "  addr_postal_code varchar(10),\n" +
                        "  constraint customer_pk_v4 primary key(email_address, username)\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists tbl_task_v4 (\n" +
                        "  num int NOT null,\n" +
                        "  name varchar(50) not null,\n" +
                        "  done boolean not null default false,\n" +
                        "  task_created timestamp not null default now(),\n" +
                        "  parent_task_num int,\n" +
                        "  parent_task_name varchar(50),\n" +
                        "  constraint task_pk_v4 primary key(num, name),\n" +
                        "  constraint task_parent_fk_v4 foreign key(parent_task_num, parent_task_name) references tbl_task_v4(num, name)\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists tbl_user_task_v4 (\n" +
                        "    task_num int not null,\n" +
                        "    task_name varchar(50) not NULL,\n" +
                        "    task_assignee_email varchar(100) not null,\n" +
                        "    task_assignee_uname varchar(50) NOT null,\n" +
                        "    CONSTRAINT task_fk_v4 FOREIGN key(task_num, task_name) REFERENCES tbl_task_v4(num, name),\n" +
                        "  \tCONSTRAINT task_assignee_fk_v4 FOREIGN key(task_assignee_email, task_assignee_uname) REFERENCES tbl_user_v4(email_address, username)\n" +
                        ");\n" +
                        "\n" +
                        "INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user1@email.com', 'one', 'user one');\n" +
                        "INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user2@email.com', 'two', 'user two');\n" +
                        "INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user3@email.com', 'three', 'user three');\n" +
                        "INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user4@email.com', 'four', 'user four');\n" +
                        "INSERT INTO TBL_USER_v4 (email_address, username, NICKNAME) VALUES ('user5@email.com', 'five', 'user five');\n" +
                        "\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 1', 1);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 2', 2);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 3', 3);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 4', 4);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 5', 5);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 6', 6);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 7', 7);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 8', 8);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 9', 9);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 10', 10);\n" +
                        "INSERT INTO TBL_TASK_v4 (name, num) VALUES ('task 11', 11);\n" +
                        "\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 1', PARENT_TASK_NUM = 1 WHERE name = 'task 2' AND num = 2;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 2', PARENT_TASK_NUM = 2  WHERE name = 'task 3' AND num = 3;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 3', PARENT_TASK_NUM = 3  WHERE name = 'task 4' AND num = 4;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 4', PARENT_TASK_NUM = 4  WHERE name = 'task 5' AND num = 5;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 5', PARENT_TASK_NUM = 5  WHERE name = 'task 6' AND num = 6;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 6', PARENT_TASK_NUM = 6  WHERE name = 'task 7' AND num = 7;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 7', PARENT_TASK_NUM = 7  WHERE name = 'task 8' AND num = 8;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 8', PARENT_TASK_NUM = 8  WHERE name = 'task 9' AND num = 9;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 9', PARENT_TASK_NUM = 9  WHERE name = 'task 10' AND num = 10;\n" +
                        "UPDATE TBL_TASK_v4 SET PARENT_TASK_NAME = 'task 10', PARENT_TASK_NUM = 10  WHERE name = 'task 11' AND num = 11;\n" +
                        "\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (2, 'task 2', 'user1@email.com', 'one');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (3, 'task 3', 'user1@email.com', 'one');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (4, 'task 4', 'user2@email.com', 'two');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (5, 'task 5', 'user2@email.com', 'two');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (6, 'task 6', 'user3@email.com', 'three');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (7, 'task 7', 'user3@email.com', 'three');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (8, 'task 8', 'user4@email.com', 'four');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (9, 'task 9', 'user4@email.com', 'four');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (10, 'task 10', 'user1@email.com', 'one');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (11, 'task 11', 'user2@email.com', 'two');\n" +
                        "INSERT INTO TBL_USER_TASK_v4 (TASK_NUM, TASK_NAME, TASK_ASSIGNEE_EMAIL, TASK_ASSIGNEE_UNAME) values (1, 'task 1', 'user3@email.com', 'three');\n"
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
        String sql = "insert into tbl_task_v4 (name) values ('" + name + "')";
        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
//        initializeData("Wake up");
//        initializeData("Shower");
//        initializeData("Eat Breakfast");
    }
}
