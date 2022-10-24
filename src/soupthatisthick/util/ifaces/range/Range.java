package soupthatisthick.util.ifaces.range;

import java.util.Optional;

public interface Range<T> {
    Optional<T> getPrevious(T position);
    Optional<T> getNext(T position);
    T getFirst();
    T getLast();
    boolean contains(T value);
}
