package works.hop.jdbc.s_3_select_composite_pk;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
