package soupthatisthick.util.impl.relations;

import soupthatisthick.util.ifaces.relations.ManyToMany;

import java.util.Optional;
import java.util.Set;

public class SomeToSomeImpl<Source, Target> implements ManyToMany<Source, Target> {
    private ManyToMany<Source, Target> manyToMany = new ManyToManyImpl<>();

    private Optional<Integer> maxSource;
    private Optional<Integer> maxTarget;

    public static final String ERROR_MAX_SOURCES_REACHED = "Can't exceed maximum number of sources.";
    public static final String ERROR_MAX_TARGETS_REACHED = "Can't exceed maximum number of targets.";



    @Override
    public void addSource(Source source) {
        final Set<Source> sources = manyToMany.getSources();
        if (sources.contains(source)) {
            return;
        }
        if (sources.size() < getMaxSource().orElse(sources.size()+1)) {
            manyToMany.addSource(source);
        } else {
            throw new IllegalArgumentException(ERROR_MAX_SOURCES_REACHED);
        }
    }

    @Override
    public void addTarget(Target target) {
        final Set<Target> targets = manyToMany.getTargets();
        if (targets.contains(target)) {
            return;
        }
        if (targets.size() < getMaxTarget().orElse(targets.size()+1)) {
            manyToMany.addTarget(target);
        } else {
            throw new IllegalArgumentException(ERROR_MAX_TARGETS_REACHED);
        }
    }

    @Override
    public void isolateSource(Source source) {
        manyToMany.isolateSource(source);
    }

    @Override
    public void isolateTarget(Target target) {
        manyToMany.isolateTarget(target);
    }

    @Override
    public void removeSource(Source source) {
        manyToMany.removeSource(source);
    }

    @Override
    public void removeTarget(Target target) {
        manyToMany.removeTarget(target);
    }

    @Override
    public boolean isLink(Source source, Target target) {
        return manyToMany.isLink(source, target);
    }

    @Override
    public void link(Source source, Target target) {
        addSource(source);
        addTarget(target);
        manyToMany.link(source, target);
    }

    @Override
    public void unlink(Source source, Target target) {
        manyToMany.unlink(source, target);
    }

    @Override
    public Set<Target> getTargetsOf(Source source) {
        return manyToMany.getTargetsOf(source);
    }

    @Override
    public Set<Source> getSourcesOf(Target target) {
        return manyToMany.getSourcesOf(target);
    }

    @Override
    public Set<Source> getSources() {
        return manyToMany.getSources();
    }

    @Override
    public Set<Target> getTargets() {
        return manyToMany.getTargets();
    }

    @Override
    public void clear() {
        manyToMany.clear();
    }


    public Optional<Integer> getMaxSource() {
        return maxSource;
    }

    public void setMaxSource(Optional<Integer> maxSource) {
        this.maxSource = maxSource;
    }

    public Optional<Integer> getMaxTarget() {
        return maxTarget;
    }

    public void setMaxTarget(Optional<Integer> maxTarget) {
        this.maxTarget = maxTarget;
    }

}
