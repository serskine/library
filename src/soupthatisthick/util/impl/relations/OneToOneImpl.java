package soupthatisthick.util.impl.relations;

import soupthatisthick.util.ifaces.relations.OneToOne;
import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class OneToOneImpl<Key, Value> implements OneToOne<Key, Value> {

    private Map<Key, Value> keyValueMap = new HashMap<>();
    private Map<Value, Key> valueKeyMap = new HashMap<>();

    public final boolean isLink(Key key, Value value) {
        return keyValueMap.containsKey(key) && value.equals(keyValueMap.get(key));
    }

    public final void link(Key key, Value value) {
        if (keyValueMap.containsKey(key)) {
            valueKeyMap.remove(keyValueMap.get(key));
        }
        if (valueKeyMap.containsKey(value)) {
            keyValueMap.remove(valueKeyMap.get(value));
        }
        keyValueMap.remove(key);
        valueKeyMap.remove(value);

        keyValueMap.put(key, value);
        valueKeyMap.put(value, key);
    }

    @Override
    public final void unlink(Key key, Value value) {
        if (isLink(key, value)) {
            keyValueMap.remove(key);
            valueKeyMap.remove(value);
        }
    }

    @Override
    public final Set<Key> getSources() {
        return ReadOnly.set(keyValueMap.keySet());
    }

    @Override
    public final Set<Value> getTargets() {
        return ReadOnly.set(valueKeyMap.keySet());
    }

    @Override
    public final void clear() {
        keyValueMap.clear();
        valueKeyMap.clear();
    }

    @Override
    public final Optional<Value> getTargetOf(Key key) {
        return Optional.ofNullable(keyValueMap.get(key));
    }

    @Override
    public final Optional<Key> getSourceOf(Value value) {
        return Optional.ofNullable(valueKeyMap.get(value));
    }


}
