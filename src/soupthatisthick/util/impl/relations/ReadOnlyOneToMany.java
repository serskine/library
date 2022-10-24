package soupthatisthick.util.impl.relations;

import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.Optional;
import java.util.Set;

public final class ReadOnlyOneToMany<Source, Target> implements OneToMany<Source, Target> {
    public static final String ERROR_READ_ONLY = "The 1 -> many relationship is readonly";

    public final OneToMany<Source, Target> map;

    public ReadOnlyOneToMany(final OneToMany<Source, Target> map) {
        this.map = map;
    }

    @Override
    public boolean isLink(Source source, Target target) {
        return map.isLink(source, target);
    }

    @Override
    public void link(Source source, Target target) {
        throw new UnsupportedOperationException(ERROR_READ_ONLY);
    }

    @Override
    public void unlink(Source source, Target target) {
        throw new UnsupportedOperationException(ERROR_READ_ONLY);
    }

    @Override
    public Set<Target> getTargetsOf(Source source) {
        return ReadOnly.set(map.getTargetsOf(source));
    }

    @Override
    public Set<Source> getSources() {
        return ReadOnly.set(map.getSources());
    }

    @Override
    public Set<Target> getTargets() {
        return ReadOnly.set(map.getTargets());
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(ERROR_READ_ONLY);
    }

    @Override
    public void addSource(Source source) {
        throw new UnsupportedOperationException(ERROR_READ_ONLY);
    }

    @Override
    public void isolate(Source source) {
        throw new UnsupportedOperationException(ERROR_READ_ONLY);
    }

    @Override
    public Optional<Source> getSourceOf(Target target) {
        return map.getSourceOf(target);
    }

    @Override
    public void removeSource(Source source) {
        throw new UnsupportedOperationException(ERROR_READ_ONLY);
    }

    @Override
    public void removeTarget(Target target) {
        throw new UnsupportedOperationException(ERROR_READ_ONLY);
    }
}
