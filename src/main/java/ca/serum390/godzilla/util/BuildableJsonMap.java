package ca.serum390.godzilla.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

/**
 * @implNote This class delegates to {@link HashMap} just so that we can store
 *           null values in the map. This is normally not recommended, but here
 *           it is what we want due to the fact that this class is meant to
 *           store Json key-values, where the value may be null.
 */
public class BuildableJsonMap<K, V> implements Map<K, V> {
    private static ObjectMapper mapper;

    private Map<K, V> delegate;

    public BuildableJsonMap() {
        this(null);
    }

    public BuildableJsonMap(Map<K, V> map) {
        this.delegate = map == null ? null : new HashMap<>(map);
    }

    private Map<K, V> getDelegate() {
        if (delegate == null) {
            delegate = Map.of();
        }
        return delegate;
    }

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    public BuildableJsonMap<K, V> with(K key, V value) {
        return new BuildableJsonMap<>(Stream
            .concat(
                this.entrySet().stream(),
                Map.of(key, value).entrySet().stream())
            .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll));
    }

    public BuildableJsonMap<K, V> add(K key, V value) {
        return with(key, value);
    }

    public static BuildableJsonMap<Object, Object> map() {
        return new BuildableJsonMap<>();
    }

    public static <K, V> BuildableJsonMap<K, V> map(Map<K, V> map) {
        return new BuildableJsonMap<>(map);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> BuildableJsonMap<K, V> map(K seedKey, V seedValue) {
        return (BuildableJsonMap<K, V>) map().with(seedKey, seedValue);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> BuildableJsonMap<K, V> map(Class<K> keyClass, Class<V> valueClass) {
        return (BuildableJsonMap<K, V>) map();
    }

    /**
     * Convert your pojo to a BuildableJsonMap
     *
     * @param pojo
     * @return
     */
    @SuppressWarnings("unchecked")
    public static BuildableJsonMap<Object, Object> map(Object pojo) {
        try {
            ObjectMapper om = getObjectMapper();
            Map<Object, Object> map = om.readValue(om.writeValueAsString(pojo), Map.class);
            return map(map);
        } catch (JsonProcessingException e) {
            return map();
        }
    }

    /**
     * Convenience method for converting this map into a {@link Mono}
     *
     * @return A Mono of emitting this map
     */
    public Mono<BuildableJsonMap<K, V>> toMono() {
        return Mono.just(this);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public String toString() {
        try {
            return getObjectMapper().writeValueAsString(delegate);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public String prettyPrint() {
        try {
            return getObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(delegate);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    // Delegate calls -- all these methods delegate to our map instance
    @Override public void clear() { getDelegate().clear(); }
    @Override public int size() { return getDelegate().size(); }
    @Override public Set<K> keySet() { return getDelegate().keySet(); }
    @Override public V get(Object key) { return getDelegate().get(key); }
    @Override public boolean isEmpty() { return getDelegate().isEmpty(); }
    @Override public Collection<V> values() { return getDelegate().values(); }
    @Override public V remove(Object key) { return getDelegate().remove(key); }
    @Override public V put(K key, V value) { return getDelegate().put(key, value); }
    @Override public Set<Entry<K, V>> entrySet() { return getDelegate().entrySet(); }
    @Override public void putAll(Map<? extends K, ? extends V> m) { getDelegate().putAll(m); }
    @Override public boolean containsKey(Object key) { return getDelegate().containsKey(key); }
    @Override public boolean containsValue(Object value) { return getDelegate().containsValue(value); }
}
