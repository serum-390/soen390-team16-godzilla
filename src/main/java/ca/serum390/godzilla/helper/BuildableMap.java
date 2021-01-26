package ca.serum390.godzilla.helper;

import java.util.HashMap;
import java.util.Map;

public class BuildableMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = -6742016711010280219L;

    public BuildableMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public BuildableMap() {
    }

    public BuildableMap(int initialCapacity) {
        super(initialCapacity);
    }

    public BuildableMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public BuildableMap<K, V> with(K key, V value) {
        this.put(key, value);
        return this;
    }

    public static BuildableMap<Object, Object> map() {
        return new BuildableMap<>();
    }
}
