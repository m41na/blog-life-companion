package works.hop.jdbc.s_7_select_refactoring;

import java.util.HashMap;
import java.util.Optional;

public class LocalCache<VAL> extends HashMap<Object, VAL> {

    //key MUST implement equals/hashcode for correct behavior
    public void addIfNotExists(Object key, VAL value) {
        if (!containsKey(key)) {
            put(key, value);
        }
    }

    //key MUST implement equals/hashcode for correct behavior
    public Optional<VAL> getIfExists(Object key) {
        return Optional.ofNullable(get(key));
    }
}
