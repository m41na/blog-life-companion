package works.hop.jdbc.s_8_validate_metadata;

import java.util.List;

public class OffsetLimits {

    List<OffsetLimit> offsetLimits;

    public OffsetLimits(List<OffsetLimit> offsetLimits) {
        this.offsetLimits = offsetLimits;
    }

    public static OffsetLimitsBuilder builder() {
        return new OffsetLimitsBuilder();
    }
}
