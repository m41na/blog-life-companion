package works.hop.jdbc.i_3_insert_composite_key;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);

    Object get(String attribute);
}
