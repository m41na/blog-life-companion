package works.hop.jdbc.s_6_select_join_different_tables;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
