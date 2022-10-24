package soupthatisthick.util.impl.relations;

import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ManyToManyImpl<Source, Target> implements ManyToMany<Source, Target> {
    private final Map<Source, Set<Target>> sourceTargetMap = new HashMap<>();
    private final Map<Target, Set<Source>> targetSourceMap = new HashMap<>();


    @Override
    public final void addSource(Source source) {
        sourceTargetMap.putIfAbsent(source, new HashSet<>());
    }

    @Override
    public final void addTarget(Target target) {
        targetSourceMap.putIfAbsent(target, new HashSet<>());
    }

    @Override
    public final void isolateSource(Source source) {
        sourceTargetMap.get(source).clear();
        for(Target target : targetSourceMap.keySet()) {
            targetSourceMap.get(target).remove(source);
        }
    }

    @Override
    public final void isolateTarget(Target target) {
        targetSourceMap.get(target).clear();
        for(Source source : sourceTargetMap.keySet()) {
            sourceTargetMap.get(source).remove(target);
        }
    }

    @Override
    public final void removeSource(Source source) {
        isolateSource(source);
        sourceTargetMap.remove(source);
    }

    @Override
    public final void removeTarget(Target target) {
        isolateTarget(target);
        targetSourceMap.remove(target);
    }

    @Override
    public final void clear() {
        sourceTargetMap.clear();
        targetSourceMap.clear();
    }

    @Override
    public final boolean isLink(Source source, Target target) {
        return sourceTargetMap.getOrDefault(source, new HashSet<>()).contains(target);
    }

    @Override
    public final void link(Source source, Target target) {
        addSource(source);
        addTarget(target);
        sourceTargetMap.get(source).add(target);
        targetSourceMap.get(target).add(source);

    }

    @Override
    public final void unlink(Source source, Target target) {
        isolateSource(source);
        isolateTarget(target);
        sourceTargetMap.remove(source);
        targetSourceMap.remove(target);
    }

    @Override
    public final Set<Target> getTargetsOf(Source source) {
        return ReadOnly.set(sourceTargetMap.getOrDefault(source, new HashSet<>()));
    }

    @Override
    public final Set<Source> getSourcesOf(Target target) {
        return ReadOnly.set(targetSourceMap.getOrDefault(target, new HashSet<>()));
    }

    @Override
    public final Set<Source> getSources() {
        return ReadOnly.set(sourceTargetMap.keySet());
    }

    @Override
    public final Set<Target> getTargets() {
        return ReadOnly.set(targetSourceMap.keySet());
    }
}
