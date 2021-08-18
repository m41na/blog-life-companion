package works.hop.jdbc.s_8_validate_metadata;

public class OffsetLimitBuilder {

    private int offset = 0;
    private int limit = 20;
    private String[] attributes;

    public OffsetLimitBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public OffsetLimitBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public OffsetLimitBuilder attributes(String[] attributes) {
        this.attributes = attributes;
        return this;
    }

    public OffsetLimitBuilder attribute(String attribute) {
        String[] newArray = new String[attributes.length + 1];
        System.arraycopy(this.attributes, 0, newArray, 0, attributes.length);
        this.attributes = newArray;
        this.attributes[attributes.length - 1] = attribute;
        return this;
    }

    public OffsetLimit build() {
        return new OffsetLimit(offset, limit, attributes);
    }
}
