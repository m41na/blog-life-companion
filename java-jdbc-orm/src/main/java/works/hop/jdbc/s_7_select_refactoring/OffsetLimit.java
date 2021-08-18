package works.hop.jdbc.s_7_select_refactoring;

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
