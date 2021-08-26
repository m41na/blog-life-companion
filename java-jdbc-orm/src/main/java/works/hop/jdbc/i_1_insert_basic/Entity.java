package works.hop.jdbc.i_1_insert_basic;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);

    Object get(String attribute);
}
