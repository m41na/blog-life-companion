package works.hop.jdbc.s_3_select_composite_pk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupTable {

    private static final String connectionString = "jdbc:h2:./data/sample-3.db";

    public static void createTable() {
        String[] queries = new String[]{
                "drop table if exists tbl_user;",
                "create table if not exists tbl_user (\n" +
                        "  email_address varchar(100) not null,\n" +
                        "  username varchar(50) not null,\n" +
                        "  nickname varchar(50),\n" +
                        "  date_joined timestamp not null default now(),\n" +
                        "  access_level varchar default 'VISITOR',\n" +
                        "  addr_city varchar(50),\n" +
                        "  addr_state varchar(30),\n" +
                        "  addr_postal_code varchar(10),\n" +
                        "  constraint customer_pk primary key(email_address, username)\n" +
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

    public static void initializeData(String emailAddress, String userName, String nickName, String city, String state, String zipCode) {
        String sql = "insert into tbl_user (email_address, username, nickname, addr_city, addr_state, addr_postal_code) values " +
                "('" + emailAddress + "', '" + userName + "', '" + nickName + "', '" + city + "', '" + state + "', '" + zipCode + "')";
        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
        initializeData("one@email.com", "One", "Moja", "Chicago", "IL", "60063");
        initializeData("one@email.com", "Two", "Mbili", "Davenport", "IA", "55430");
        initializeData("one@email.com", "Three", "Tatu", "Madison", "WI", "53718");
    }
}
