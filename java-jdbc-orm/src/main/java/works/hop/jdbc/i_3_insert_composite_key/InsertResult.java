package works.hop.jdbc.i_3_insert_composite_key;

public class InsertResult<T extends Entity> {

    public final T result;
    public final String error;

    public InsertResult(T result, String error) {
        this.result = result;
        this.error = error;
    }

    public static <T extends Entity> InsertResult<T> success(T result) {
        return new InsertResult<>(result, null);
    }

    public static <T extends Entity> InsertResult<T> failure(Throwable error) {
        return new InsertResult<>(null, error.getMessage() != null ? error.getMessage() : error.getClass().getName());
    }
}
