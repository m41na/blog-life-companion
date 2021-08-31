package works.hop.jdbc.i_6_insert_join_different_tables;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);

    Object get(String attribute);
}
