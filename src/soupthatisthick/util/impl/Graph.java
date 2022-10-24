package soupthatisthick.util.impl;

import soupthatisthick.util.ifaces.comparison.Equatable;
import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.ifaces.relations.OneToOne;
import soupthatisthick.util.impl.relations.ManyToManyImpl;
import soupthatisthick.util.impl.relations.OneToOneImpl;

import java.util.*;

public class Graph<Vertex, Link> {

    private final ManyToMany<Vertex, Vertex> relationship = new ManyToManyImpl<>();
    private final OneToOne<Entry, Link> paths = new OneToOneImpl<>();

    public class Entry implements Equatable {
        public final Vertex source;
        public final Vertex target;

        public Entry(final Vertex source, final Vertex target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public boolean equals(Object other) {
            return isEquivalentTo(other);
        }

        @Override
        public boolean isEqualTo(Object other) {
            return this == other;
        }

        @Override
        public boolean isEquivalentTo(Object other) {
            if (other==null) {
                return false;
            } else if (other instanceof Equatable) {
                final Entry otherEntry = (Entry) other;
                return  Objects.equals(this.source, otherEntry.source)
                        && Objects.equals(this.target, otherEntry.target);
            } else {
                return false;
            }
        }
    }

    public void addVertex(final Vertex vertex) {
        relationship.addSource(vertex);
        relationship.addTarget(vertex);
    }

    public void removeVertex(final Vertex vertex) {
        relationship.removeSource(vertex);
        relationship.removeTarget(vertex);
    }

    public void isolate(final Vertex vertex) {
        relationship.isolateSource(vertex);
    }

    /**
     * This will add a link from the parent to the child.
     * @param source
     * @param destination
     */
    public void link(final Vertex source, final Vertex destination, final Link value) {
        relationship.link(source, destination);
    }

    /**
     * This will remove an existing link
     * @param source
     * @param destination
     */
    public void unlink(final Vertex source, final Vertex destination) {
        relationship.unlink(source, destination);
    }

    public boolean hasLink(final Vertex source, final Vertex destination) {
        return relationship.isLink(source, destination);
    }

    public Link getLinkOrDefault(final Vertex source, final Vertex destination, final Link defaultLink) {
        if (relationship.isLink(source, destination)) {
            return paths.getTargetOf(new Entry(source, destination)).orElseGet(() -> defaultLink);
        }
        return defaultLink;
    }

    public Map<Vertex, Link> getPathsTo(final Vertex destination) {
        final Set<Vertex> sources = relationship.getSourcesOf(destination);
        final Map<Vertex, Link> map = new HashMap<>();
        for(Vertex source : sources) {
            final Entry entry = new Entry(source, destination);
            final Optional<Link> valueOpt = paths.getTargetOf(entry);
            if (valueOpt.isPresent()) {
                map.put(source, valueOpt.get());
            }
        }
        return map;
    }

    public Map<Vertex, Link> getPathsFrom(final Vertex source) {
        final Set<Vertex> targets = relationship.getTargetsOf(source);
        final Map<Vertex, Link> map = new HashMap<>();
        for(Vertex target : targets) {
            final Entry entry = new Entry(source, target);
            final Optional<Link> valueOpt = paths.getTargetOf(entry);
            if (valueOpt.isPresent()) {
                map.put(target, valueOpt.get());
            }
        }
        return map;
    }

    public Set<Vertex> getVerticies() {
        return Collections.unmodifiableSet(relationship.getSources());
    }
}
