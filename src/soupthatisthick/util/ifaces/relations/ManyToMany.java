package soupthatisthick.util.ifaces.relations;

public interface ManyToMany<Source, Target> extends Relationship<Source, Target> {
    void addSource(Source source);
    void addTarget(Target target);
    void isolateSource(Source source);
    void isolateTarget(Target target);
    void removeSource(Source source);
    void removeTarget(Target target);
}
