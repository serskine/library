package soupthatisthick.ai.dmatrix;

import java.util.Comparator;

public abstract class MeasurableOutcomeMatrix<Action, Outcome> extends DecisionMatrix<Action, Outcome> {
    public abstract double getResultMeasure(final Result<Action, Outcome> result);

    public double getActionValue(final Action action) {
        return manyToMany.getTargetsOf(action).stream()
                .map(outcome -> new Result(action, outcome))
                .mapToDouble(result -> getResultMeasure(result))
                .sum();
    }

    @Override
    protected Comparator<Action> getActionComparator() {
        return new Comparator<Action>() {
            @Override
            public int compare(Action a1, Action a2) {
                final double v1 = getActionValue(a1);
                final double v2 = getActionValue(a2);
                return Double.compare(v1, v2);
            }
        };
    }
}
