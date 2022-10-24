package soupthatisthick.util.impl.relations;

import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.Set;

public final class ReadOnlyManyToMany<Source, Target> implements ManyToMany<Source, Target> {

    public static final String ERROR_READONLY = "Many to Many relationship is read only!";
    private final ManyToMany<Source, Target> manyToMany;

    public ReadOnlyManyToMany(final ManyToMany<Source, Target> manyToMany) {
        this.manyToMany = manyToMany;
    }

       @Override
    public void addSource(Source source) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void addTarget(Target target) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void isolateSource(Source source) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void isolateTarget(Target target) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void removeSource(Source source) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void removeTarget(Target target) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public boolean isLink(Source source, Target target) {
        return manyToMany.isLink(source, target);
    }

    @Override
    public void link(Source source, Target target) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public void unlink(Source source, Target target) {
        throw new UnsupportedOperationException(ERROR_READONLY);
    }

    @Override
    public Set<Target> getTargetsOf(Source source) {
        return ReadOnly.set(manyToMany.getTargetsOf(source));
    }

    @Override
    public Set<Source> getSourcesOf(Target target) {
        return ReadOnly.set(manyToMany.getSourcesOf(target));
    }

    @Override
    public Set<Source> getSources() {
        return ReadOnly.set(manyToMany.getSources());
    }

    @Override
    public Set<Target> getTargets() {
        return ReadOnly.set(manyToMany.getTargets());
    }
}
