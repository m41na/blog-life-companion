package works.hop.jdbc.s_8_validate_metadata;

public interface Entity {

    EntityMetadata metadata();

    <T> void set(String attribute, T value);
}
