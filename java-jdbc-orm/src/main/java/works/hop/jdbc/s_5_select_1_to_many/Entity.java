package works.hop.jdbc.s_5_select_1_to_many;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
