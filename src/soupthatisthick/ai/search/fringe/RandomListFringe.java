package soupthatisthick.ai.search.fringe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomListFringe<State> extends Fringe<State> {
    private static final Random R = new Random();

    final List<State> theList = new ArrayList<>();

    @Override
    public void addAll(final Iterator<State> itr) {
        while(itr.hasNext()) {
            theList.add(itr.next());
        }
    }

    @Override
    public boolean hasNext() {
        return !theList.isEmpty();
    }

    @Override
    public State next() {
        final int index = chooseIndex(theList.size());
        final State choice = theList.remove(index);
        return choice;
    }

    public int chooseIndex(final int numItems) {
        return R.nextInt(numItems);
    }
}
