package works.hop.jdbc.i_2_insert_embedded;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupTable {

    private static final String connectionString = "jdbc:h2:./data/sample-i_2.db";

    public static void createTable() {
        String[] queries = new String[]{
                "drop table if exists tbl_customer;",
                "create table if not exists tbl_customer (\n" +
                        "  member_id UUID default random_uuid(),\n" +
                        "  first_name varchar(50) not null,\n" +
                        "  last_name varchar(50) not null,\n" +
                        "  date_joined timestamp not null default now(),\n" +
                        "  member_level varchar default 'SILVER',\n" +
                        "  addr_city varchar(50),\n" +
                        "  addr_state varchar(30),\n" +
                        "  addr_postal_code varchar(10),\n" +
                        "  constraint customer_pk primary key(member_id)\n" +
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

    public static void initializeData(String firstName, String lastNAme, String city, String state, String zipCode) {
        String sql = "insert into tbl_customer (first_name, last_name, addr_city, addr_state, addr_postal_code) values " +
                "('" + firstName + "', '" + lastNAme + "', '" + city + "', '" + state + "', '" + zipCode + "')";
        try (Connection conn = DriverManager.getConnection(connectionString);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
        initializeData("Person", "One", "Chicago", "IL", "60063");
        initializeData("Person", "Two", "Davenport", "IA", "55430");
        initializeData("Person", "Three", "Madison", "WI", "53718");
    }
}
