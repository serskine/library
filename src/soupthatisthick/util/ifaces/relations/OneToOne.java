package soupthatisthick.util.ifaces.relations;

import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface OneToOne<Source, Target> extends Relationship<Source, Target> {
    Optional<Target> getTargetOf(Source source);
    Optional<Source> getSourceOf(Target target);

    @Override
    default Set<Target> getTargetsOf(Source source) {
        final Set<Target> targetSet = new HashSet<>();
        final Optional<Target> valueOpt = getTargetOf(source);
        if (valueOpt.isPresent()) {
            targetSet.add(valueOpt.get());
        }
        return ReadOnly.set(targetSet);
    }

    @Override
    default Set<Source> getSourcesOf(Target target) {
        final Set<Source> sourceSet = new HashSet<>();
        final Optional<Source> keyOpt = getSourceOf(target);
        if (keyOpt.isPresent()) {
            sourceSet.add(keyOpt.get());
        }
        return ReadOnly.set(sourceSet);
    }
}
