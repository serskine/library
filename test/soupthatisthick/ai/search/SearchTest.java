package soupthatisthick.ai.search;

import org.junit.jupiter.api.Test;
import soupthatisthick.BaseTest;
import soupthatisthick.ai.search.fringe.BreadthFirstFringe;
import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.impl.relations.ManyToManyImpl;
import soupthatisthick.util.logger.Logger;

import java.util.Iterator;

import static soupthatisthick.ai.search.SearchTest.State.*;

public class SearchTest extends BaseTest {

    public enum State {
        S1, S2, S3, S4, S5;

        public int index() {
            for(int i=0; i<values().length; i++) {
                if (values()[i] == this) {
                    return i;
                }
            }
            throw new RuntimeException("ERROR! State not found!");
        }
    }

    private ManyToMany<State, State> map = new ManyToManyImpl<>();

    private Search<State> search;

    @Test
    public void onSetup() {
        map.link(S1, S2);
        map.link(S1, S3);
    }

    @Override
    protected void onTearDown() {
        Logger.info("Search := " + this.search.getClass().getSimpleName());
        while(this.search.hasNext()) {
            State state = this.search.next();
            Logger.info(" -> " + state);
        }

    }

    @Test
    public void testHillClimbing() {
        this.search = new Search<State>(new BreadthFirstFringe()) {
            @Override
            public Iterator<State> getChildren(State state) {
                return getNeighbours(state);
            }
        };

    }

    public Iterator<State> getNeighbours(State state) {
        return map.getTargetsOf(state).iterator();
    }
}
