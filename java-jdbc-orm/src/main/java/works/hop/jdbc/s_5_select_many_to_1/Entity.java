package works.hop.jdbc.s_5_select_many_to_1;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
