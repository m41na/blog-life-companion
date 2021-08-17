package works.hop.jdbc.s_7_select_refactoring;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
