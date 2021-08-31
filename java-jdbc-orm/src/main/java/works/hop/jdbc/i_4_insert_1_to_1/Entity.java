package works.hop.jdbc.i_4_insert_1_to_1;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);

    Object get(String attribute);
}
