package soupthatisthick.util.impl.relations;

import soupthatisthick.util.ifaces.relations.OneToOne;
import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.Optional;
import java.util.Set;

public final class ReadOnlyOneToOne<Key, Value> implements OneToOne<Key, Value> {
    private final OneToOne<Key, Value> relation;

    public static final String ERROR_READONLY = "The 1 -> 1 relationship is read only!";

    public ReadOnlyOneToOne(final OneToOne<Key, Value> relation) {
        this.relation = relation;
    }

    @Override
    public boolean isLink(Key key, Value value) {
        return relation.isLink(key, value);
    }

    @Override
    public void link(Key key, Value value) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void unlink(Key key, Value value) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public Set<Key> getSources() {
        return ReadOnly.set(relation.getSources());
    }

    @Override
    public Set<Value> getTargets() {
        return ReadOnly.set(relation.getTargets());
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public Optional<Value> getTargetOf(Key key) {
        return relation.getTargetOf(key);
    }

    @Override
    public Optional<Key> getSourceOf(Value value) {
        return relation.getSourceOf(value);
    }
}
