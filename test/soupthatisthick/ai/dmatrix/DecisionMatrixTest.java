package soupthatisthick.ai.dmatrix;

import org.junit.jupiter.api.Test;
import soupthatisthick.BaseTest;
import soupthatisthick.util.logger.Logger;

import java.util.Comparator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static soupthatisthick.ai.dmatrix.RankableOutcomeMatrix.Type.MAX;

public class DecisionMatrixTest extends BaseTest {

    public enum Action {
        ACTION_1,
        ACTION_2,
        ACTION_3,
        ACTION_4;

        public static final int indexOf(Action action) {
            for(int i=0; i<values().length; i++) {
                if (values()[i] == action) {
                    return i;
                }
            }
            throw new RuntimeException("Action not found!");
        }
    }

    public enum Outcome {
        OUTCOME_1,
        OUTCOME_2,
        OUTCOME_3,
        OUTCOME_4;

        public static final int indexOf(Outcome outcome) {
            for(int i=0; i<values().length; i++) {
                if (values()[i] == outcome) {
                    return i;
                }
            }
            throw new RuntimeException("Outcome not found!");
        }
    }

    private DecisionMatrix<Action, Outcome> decisionMatrix;

    @Override
    protected void onSetup() {

    }

    @Override
    protected void onTearDown() {

    }

    @Test
    public void rankable() {
        decisionMatrix = new DecisionMatrix<Action, Outcome>() {
            @Override
            protected Comparator<Action> getActionComparator() {
                return new Comparator<Action>() {
                    @Override
                    public int compare(Action a1, Action a2) {
                        return Action.indexOf(a1) - Action.indexOf(a2);
                    }
                };
            }
        };


        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_2, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_3);
        decisionMatrix.manyToMany.link(Action.ACTION_4, Outcome.OUTCOME_4);

        final Optional<Action> choice = decisionMatrix.getBestAction();
        Logger.info("Choice := " + choice);

        assertEquals(Action.ACTION_4, choice);
    }

    @Test
    public void rankableOutcome() {
        decisionMatrix = new RankableOutcomeMatrix<Action, Outcome>(MAX) {

            @Override
            protected Comparator<Result<Action, Outcome>> getResultComparator(Type type) {
                return new Comparator<Result<Action, Outcome>>() {

                    @Override
                    public int compare(Result<Action, Outcome> r1, Result<Action, Outcome> r2) {
                        return (Outcome.values().length - Outcome.indexOf(r1.outcome)) * Action.indexOf(r1.action)
                            - (Outcome.values().length - Outcome.indexOf(r2.outcome)) * Action.indexOf(r2.action);
                    }
                };
            }
        };

        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_3);

        decisionMatrix.manyToMany.link(Action.ACTION_2, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_2);

        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_3);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_4);

        decisionMatrix.manyToMany.link(Action.ACTION_4, Outcome.OUTCOME_4);

        final Optional<Action> choice = decisionMatrix.getBestAction();
        Logger.info("Choice := " + choice);

        assertEquals(Action.ACTION_3, choice.get());
    }

    @Test
    public void measurableOutcome() {
        decisionMatrix = new MeasurableOutcomeMatrix<Action, Outcome>() {

            @Override
            public double getResultMeasure(Result<Action, Outcome> result) {
                return Outcome.indexOf(result.outcome) * Action.indexOf(result.action);
            }
        };

        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_2, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_3);
        decisionMatrix.manyToMany.link(Action.ACTION_4, Outcome.OUTCOME_4);

        final Optional<Action> choice = decisionMatrix.getBestAction();
        Logger.info("Choice := " + choice);

        assertEquals(Action.ACTION_3, choice.get());
    }


    @Test
    public void expectedValue() {
        decisionMatrix = new ExpectedValueMatrix<Action, Outcome>() {

            @Override
            public double getResultProbability(Result<Action, Outcome> result) {
                return (Action.indexOf(result.action) == Outcome.indexOf(result.outcome)) ? 0D : 1D;
            }

            @Override
            public double getResultValue(Result<Action, Outcome> result) {
                return Math.pow(2, Outcome.indexOf(result.outcome));
            }
        };

        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_3);
        decisionMatrix.manyToMany.link(Action.ACTION_1, Outcome.OUTCOME_4);

        decisionMatrix.manyToMany.link(Action.ACTION_2, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_2, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_2, Outcome.OUTCOME_3);
        decisionMatrix.manyToMany.link(Action.ACTION_2, Outcome.OUTCOME_4);

        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_3);
        decisionMatrix.manyToMany.link(Action.ACTION_3, Outcome.OUTCOME_4);

        decisionMatrix.manyToMany.link(Action.ACTION_4, Outcome.OUTCOME_1);
        decisionMatrix.manyToMany.link(Action.ACTION_4, Outcome.OUTCOME_2);
        decisionMatrix.manyToMany.link(Action.ACTION_4, Outcome.OUTCOME_3);
        decisionMatrix.manyToMany.link(Action.ACTION_4, Outcome.OUTCOME_4);

        final Optional<Action> choice = decisionMatrix.getBestAction();
        Logger.info("Choice := " + choice);

        assertEquals(Action.ACTION_1, choice.get());
    }
}
