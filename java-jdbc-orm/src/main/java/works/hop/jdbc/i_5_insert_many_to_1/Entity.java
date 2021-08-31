package works.hop.jdbc.i_5_insert_many_to_1;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);

    Object get(String attribute);
}
