package works.hop.jdbc.s_6_select_join_different_tables;

import java.util.HashMap;
import java.util.Optional;

public class LocalCache<VAL> extends HashMap<Comparable<?>, VAL> {

    public void addIfNotExists(Comparable<?> key, VAL value) {
        if (!containsKey(key)) {
            put(key, value);
        }
    }

    public Optional<VAL> getIfExists(Comparable<?> key) {
        return Optional.ofNullable(get(key));
    }
}
