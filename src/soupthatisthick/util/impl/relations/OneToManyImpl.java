package soupthatisthick.util.impl.relations;

import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.*;

public class OneToManyImpl<Source, Target> implements OneToMany<Source, Target> {

    private final Map<Target, Source> targetMap = new HashMap<>();
    private final Map<Source, Set<Target>> sourceMap = new HashMap<>();


    @Override
    public final boolean isLink(Source source, Target target) {
        return getTargetsOf(source).contains(target);
    }

    @Override
    public final void link(Source source, Target target) {
        final Optional<Source> prevParentOpt = getSourceOf(target);
        if (prevParentOpt.isPresent()) {
            final Source prevSource = prevParentOpt.get();
            if (!source.equals(prevSource)) {
                unlink(prevSource, target);
            }
        }
        addSource(source);
        sourceMap.get(source).add(target);
        targetMap.put(target, source);
    }

    @Override
    public final void unlink(Source source, Target target) {
        sourceMap.get(source).remove(target);
        Source prevSource = targetMap.get(source);
        if (target.equals(prevSource)) {
            targetMap.remove(target);
        }
    }

    @Override
    public final Set<Target> getTargetsOf(Source source) {
        return ReadOnly.set(sourceMap.getOrDefault(source, new HashSet<>()));
    }

    @Override
    public final Set<Source> getSources() {
        return ReadOnly.set(sourceMap.keySet());
    }

    @Override
    public final Set<Target> getTargets() {
        return ReadOnly.set(targetMap.keySet());
    }

    @Override
    public final void clear() {
        targetMap.clear();
        sourceMap.clear();
    }

    @Override
    public final void addSource(Source source) {
        sourceMap.putIfAbsent(source, new HashSet<>());
    }

    @Override
    public final void isolate(Source source) {
        if (sourceMap.containsKey(source)) {
            final Set<Target> targets = sourceMap.get(source);
            for(Target target : targets) {
                targetMap.remove(target);
            }
            targets.clear();
        }
    }

    @Override
    public final Optional<Source> getSourceOf(Target target) {
        return Optional.ofNullable(targetMap.get(target));
    }

    @Override
    public final void removeSource(Source source) {
        isolate(source);
        sourceMap.remove(source);
    }

    @Override
    public final void removeTarget(Target target) {
        if (targetMap.containsKey(target)) {
            final Source source = targetMap.get(target);
            sourceMap.get(source).remove(target);
            targetMap.remove(target);
        }
    }


}