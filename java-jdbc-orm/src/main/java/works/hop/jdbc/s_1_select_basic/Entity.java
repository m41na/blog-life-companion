package works.hop.jdbc.s_1_select_basic;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
