package soupthatisthick.ai.search.fringe;

import java.util.Iterator;

public abstract class Fringe<State> implements Iterator<State> {

    public abstract void addAll(final Iterator<State> itr);

}
