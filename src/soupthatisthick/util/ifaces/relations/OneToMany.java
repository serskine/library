package soupthatisthick.util.ifaces.relations;

import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface OneToMany <Source, Target> extends Relationship <Source, Target> {
    void addSource(Source source);
    void isolate(Source source);

    @Override
    default Set<Source> getSourcesOf(Target target) {
        final Set<Source> sourceSet = new HashSet<>();
        final Optional<Source> sourceOpt = getSourceOf(target);
        if (sourceOpt.isPresent()) {
            sourceSet.add(sourceOpt.get());
        }
        return ReadOnly.set(sourceSet);
    }

    Optional<Source> getSourceOf(Target target);
    void removeSource(Source source);
    void removeTarget(Target target);
}
