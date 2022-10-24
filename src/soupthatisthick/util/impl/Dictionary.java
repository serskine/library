package soupthatisthick.util.impl;

import java.util.*;

public class Dictionary<Key, Value> implements Map<Key, Value> {

    private Map<Key, Value> map = new HashMap<>();
    private final Value defaultValue;

    public Dictionary(final Value defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Value get(Object key) {
        return map.getOrDefault(key, defaultValue);
    }

    @Override
    public Value put(Key key, Value value) {
        final Value prev = get(key);
        if (defaultValue.equals(value)) {
            map.remove(key);
        } else {
            map.put(key, value);
        }
        return prev;
    }

    @Override
    public Value remove(Object key) {
        final Value prev = get(key);
        map.remove(key);
        return prev;
    }

    @Override
    public void putAll(Map<? extends Key, ? extends Value> m) {
        m.entrySet().forEach(entry -> put(entry.getKey(), entry.getValue()));
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Key> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @Override
    public Collection<Value> values() {
        return Collections.unmodifiableCollection(map.values());
    }

    @Override
    public Set<Entry<Key, Value>> entrySet() {
        return Collections.unmodifiableSet(map.entrySet());
    }
}
