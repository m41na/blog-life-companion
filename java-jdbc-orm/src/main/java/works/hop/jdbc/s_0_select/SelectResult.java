package works.hop.jdbc.s_0_select;

import java.util.Collection;
import java.util.Collections;

public class SelectResult<T> {

    public final Collection<T> result;
    public final String error;

    public SelectResult(Collection<T> result, String error) {
        this.result = result;
        this.error = error;
    }

    public static <T> SelectResult<T> success(Collection<T> result) {
        return new SelectResult<>(result, null);
    }

    public static <T> SelectResult<T> failure(Throwable error) {
        return new SelectResult<>(Collections.emptyList(), error.getMessage() != null ? error.getMessage() : error.getClass().getName());
    }
}
