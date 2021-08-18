package works.hop.jdbc.s_7_select_refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OffsetLimitsBuilder {

    private final List<OffsetLimit> offsetLimits = new ArrayList<>();

    public OffsetLimitsBuilder offsetLimit(Function<OffsetLimitBuilder, OffsetLimit> builder) {
        offsetLimits.add(builder.apply(new OffsetLimitBuilder()));
        return this;
    }

    public OffsetLimitsBuilder offsetLimit(int offset, int limit, String... attributes) {
        this.offsetLimits.add(OffsetLimit.builder().limit(limit).offset(offset).attributes(attributes).build());
        return this;
    }

    public OffsetLimits build() {
        return new OffsetLimits(offsetLimits);
    }
}
