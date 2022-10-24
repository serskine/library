package soupthatisthick.util.ifaces.relations;

import soupthatisthick.util.ifaces.comparison.Equatable;

import java.util.Set;

public interface Relationship<Source, Target> extends Equatable {

    boolean isLink(Source source, Target target);
    void link(Source source, Target target);
    void unlink(Source source, Target target);
    Set<Target> getTargetsOf(Source source);
    Set<Source> getSourcesOf(Target target);
    Set<Source> getSources();
    Set<Target> getTargets();
    void clear();

    default void copy(Relationship<Source, Target> other) {
        if (this.isEqualTo(other) ) {
            return;
        }

        if (other==null) {
            throw new IllegalArgumentException("The other relationship is null!");
        }

        if (this.getClass().equals(other.getClass())) {
            this.clear();
            for(Source source : other.getSources()) {
                for(Target target : other.getTargetsOf(source)) {
                    this.link(source, target);
                }
            }
        } else {
            throw new IllegalArgumentException("The other relationship class is not the same [" + getClass().getSimpleName() + " != " + other.getClass().getSimpleName() + "]");
        }
    }

    default boolean isEqualTo(Object other) {
        return (this==other);
    }

    default boolean isEquivalentTo(Object other) {
        if (other==null) {
            return false;
        }
        if (!getClass().equals(other.getClass())) {
            return false;
        }

        final Relationship<Source, Target> otherRelation = (Relationship<Source, Target>) other;

        if (!compareToFrom(otherRelation)) {
            return false;
        }
        if (!compareFromTo(otherRelation)) {
            return false;
        }
        return true;
    }

    default boolean compareFromTo(Relationship<Source, Target> otherRelation) {
        final Set<Source> mySources = getSources();
        final Set<Source> otherSources = otherRelation.getSources();
        final boolean isSameSources = (mySources.size()==otherSources.size())
                && mySources.containsAll(otherSources);

        if (!isSameSources) {
            return false;
        }

        for(Source source : mySources) {
            final Set<Target> mySourceTargets = getTargetsOf(source);
            final Set<Target> otherSourceTargets = getTargetsOf(source);
            final boolean isSameSourceTargets = (mySourceTargets.size()==otherSourceTargets.size())
                    && mySourceTargets.containsAll(otherSourceTargets);
            if (!isSameSourceTargets) {
                return false;
            }
        }
        return true;
    }

    default boolean compareToFrom(Relationship<Source, Target> otherRelation) {
        final Set<Target> myTargets = getTargets();
        final Set<Target> otherTargets = otherRelation.getTargets();
        final boolean isSameTargets = (myTargets.size()==otherTargets.size())
                && myTargets.containsAll(otherTargets);

        if (!isSameTargets) {
            return false;
        }

        for(Target target : myTargets) {
            final Set<Source> myTargetSources = getSourcesOf(target);
            final Set<Source> otherTargetSources = getSourcesOf(target);
            final boolean isSameTargetSources = (myTargetSources.size()==otherTargetSources.size())
                    && myTargetSources.containsAll(otherTargetSources);
            if (!isSameTargetSources) {
                return false;
            }
        }
        return true;
    }

    default String describe(final String title) {
        final StringBuilder sb = new StringBuilder();

        sb.append("\n###\n### " + title + "\n###  x " + getSources().size() + " items\n###\n");

        for(Source source : getSources()) {
            for(Target target : getTargets()) {
                sb.append(" - ")
                    .append(source)
                    .append(" => ")
                    .append(target)
                    .append(" == ")
                    .append(isLink(source, target))
                    .append("\n");
            }
        }

        return sb.toString();
    }
}
