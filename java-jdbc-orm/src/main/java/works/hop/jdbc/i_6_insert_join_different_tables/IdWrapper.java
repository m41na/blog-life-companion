package works.hop.jdbc.i_6_insert_join_different_tables;

public class IdWrapper<T extends Entity> implements Entity {

    final T delegate;

    public IdWrapper(T delegate) {
        this.delegate = delegate;
    }

    @Override
    public EntityMetadata metadata() {
        return delegate.metadata();
    }

    @Override
    public <V> void set(String attribute, V value) {
        delegate.set(attribute, value);
    }

    @Override
    public Object get(String attribute) {
        return delegate.get(attribute);
    }
}
