package soupthatisthick.ai.dmatrix;

import org.junit.jupiter.api.Test;
import soupthatisthick.BaseTest;
import soupthatisthick.util.Util;
import soupthatisthick.util.impl.Graph;
import soupthatisthick.util.logger.Logger;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static soupthatisthick.ai.dmatrix.DmatrixSearchTest.Action.A1;
import static soupthatisthick.ai.dmatrix.DmatrixSearchTest.Action.A2;
import static soupthatisthick.ai.dmatrix.DmatrixSearchTest.State.*;

public class DmatrixSearchTest extends BaseTest {

    public enum State {
        S1, S2, S3, S4, S5, S6, S7;

        public int index() {
            for(int i=0; i<State.values().length; i++) {
                if (State.values()[i] == this) {
                    return i;
                }
            }
            throw new RuntimeException("Failed to find the index of State " + this);
        }
    }

    public enum Action {
        A1, A2, A3;

        public int index() {
            for(int i=0; i<Action.values().length; i++) {
                if (Action.values()[i] == this) {
                    return i;
                }
            }
            throw new RuntimeException("Failed to find the index of Action " + this);
        }
    }

    private Graph<State, Action> map;

    private DecisionMatrix<Action, State> fringe;
    private DMatrixSearch<Action, State> search;

    public final Comparator<Action> CMP_ACTION = new Comparator<Action>() {
        @Override
        public int compare(Action a1, Action a2) {
            return a2.index() - a1.index();
        }
    };

    @Override
    protected void onSetup() {
        map = new Graph<>();
        map.link(S1, S2, A1);
        map.link(S2, S3, A1);
        map.link(S3, S4, A1);
        map.link(S3, S5, A1);
        map.link(S4, S6, A1);
        map.link(S5, S6, A1);
        map.link(S6, S7, A1);

        map.link(S1, S6, A2);

        fringe = getChildrenOf(S1);

        search = new DMatrixSearch<Action, State>(fringe) {
            @Override
            public DecisionMatrix<Action, State> getChildren(State state) {
                return getChildrenOf(state);
            }

            @Override
            public Optional<Action> chooseAction() {
                return this.fringe.getBestAction();
            }

            @Override
            public Optional<State> assumeOutcomeForAction(Action action, Set<State> possibilities) {
                return Util.chooseRandomly(possibilities.stream().toList());
            }
        };
    }

    @Override
    protected void onTearDown() {
        Logger.info("" + fringe);
    }

    @Test
    public void testSearch() {
        try {
            while(true) {
                Result<Action, State> result = search.expand();
                Logger.info(result.toString());
            }
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testFringe() {

    }

    DecisionMatrix<Action, State> getChildrenOf(State state) {
        final DecisionMatrix<Action, State> decisionMatrix = new DecisionMatrix<Action, State>() {
            @Override
            protected Comparator<Action> getActionComparator() {
                return CMP_ACTION;
            }
        };

        final Map<State, Action> paths = map.getPathsFrom(state);

        for(State target : paths.keySet()) {
            final Action action = paths.get(state);
            decisionMatrix.manyToMany.link(action, target);
        }
        return decisionMatrix;
    }

}
