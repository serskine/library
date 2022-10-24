package soupthatisthick.ai.search;

import soupthatisthick.ai.search.fringe.Fringe;

import java.util.*;

public abstract class Search<State> implements Iterator<State> {

    private final Fringe<State> fringe;

    private Optional<State> currentOpt;


    public Search(final Fringe fringe) {
        this.fringe = fringe;
        expand();
    }
    @Override
    public boolean hasNext() {
        return currentOpt.isPresent();
    }

    @Override
    public State next() {
        State state = currentOpt.orElseThrow(() -> new RuntimeException("ERROR! Search has been completed!"));
        expand();
        return state;
    }

    private void expand() {
        if (fringe.hasNext()) {
            currentOpt = Optional.of(fringe.next());
        } else {
            currentOpt = Optional.empty();
        }
    }

    public abstract Iterator<State> getChildren(State state);

}
