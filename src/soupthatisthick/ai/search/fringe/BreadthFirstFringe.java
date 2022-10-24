package soupthatisthick.ai.search.fringe;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class BreadthFirstFringe<State> extends Fringe<State> {
    private final Queue<Iterator<State>> theQueue = new ArrayDeque<>();

    @Override
    public void addAll(Iterator<State> itr) {
        theQueue.add(itr);
    }

    @Override
    public boolean hasNext() {
        while(!theQueue.isEmpty()) {
            final Iterator<State> itr = theQueue.peek();
            if (!itr.hasNext()) {
                theQueue.poll();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public State next() {
        return theQueue.peek().next();
    }
}
