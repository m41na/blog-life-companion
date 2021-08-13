package works.hop.jdbc.s_4_select_1_to_1;

import java.util.Optional;
import java.util.TreeMap;

public class LocalCache<VAL> extends TreeMap<Comparable<?>, VAL> {

    public void addIfNotExists(Comparable<?> key, VAL value) {
        if (!containsKey(key)) {
            put(key, value);
        }
    }

    public Optional<VAL> getIfExists(Comparable<?> key) {
        return Optional.ofNullable(get(key));
    }
}
