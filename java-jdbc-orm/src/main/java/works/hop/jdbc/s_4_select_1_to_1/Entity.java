package works.hop.jdbc.s_4_select_1_to_1;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
