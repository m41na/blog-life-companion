package works.hop.jdbc.s_2_select_embedded;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
