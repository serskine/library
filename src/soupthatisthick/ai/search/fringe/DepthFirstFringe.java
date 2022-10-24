package soupthatisthick.ai.search.fringe;

import java.util.Iterator;
import java.util.Stack;

public class DepthFirstFringe<State> extends Fringe<State> {
    private final Stack<Iterator<State>> theStack = new Stack<>();

    @Override
    public void addAll(Iterator<State> itr) {
        theStack.push(itr);
    }

    @Override
    public boolean hasNext() {
        while(!theStack.isEmpty()) {
            final Iterator<State> itr = theStack.peek();
            if (!itr.hasNext()) {
                theStack.pop();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public State next() {
        return theStack.peek().next();
    }
}
