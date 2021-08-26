package works.hop.jdbc.i_2_insert_embedded;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);

    Object get(String attribute);
}
