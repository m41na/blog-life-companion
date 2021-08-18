package works.hop.jdbc.s_8_validate_metadata;

public class OffsetLimit {

    int offset;
    int limit;
    String[] attributes;

    public OffsetLimit(int offset, int limit, String... attributes) {
        this.offset = offset;
        this.limit = limit;
        this.attributes = attributes;
    }

    public static OffsetLimitBuilder builder() {
        return new OffsetLimitBuilder();
    }
}
