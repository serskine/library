package soupthatisthick.ai.dmatrix;

import java.util.Comparator;
import java.util.Optional;

public abstract class RankableOutcomeMatrix<Action, Outcome> extends DecisionMatrix<Action, Outcome> {

    @Override
    protected Comparator<Action> getActionComparator() {
        return new Comparator<Action>() {
            @Override
            public int compare(Action a1, Action a2) {
                final Optional<Result<Action, Outcome>> r1 = getAssumedResult(a1);
                final Optional<Result<Action, Outcome>> r2 = getAssumedResult(a2);
                if (r1.isEmpty() && r2.isEmpty()) {
                    return 0;
                }
                if (r1.isEmpty()) {
                    return 1;
                }
                if (r2.isEmpty()) {
                    return -1;
                }
                return getResultComparator(Type.MAX).compare(r1.get(), r2.get());
            }
        };
    }

    public enum Type {
        MAX,
        MIN,
    }

    protected final Type type;

    public RankableOutcomeMatrix(final Type type) {
        this.type = type;
    }

    protected abstract Comparator<Result<Action, Outcome>> getResultComparator(final Type type);

    final Optional<Result<Action, Outcome>> getAssumedResult(final Action action) {
        return manyToMany.getTargetsOf(action).stream()
                .map(outcome -> new Result<>(action, outcome))
                .max(getResultComparator(type));
    }
}
