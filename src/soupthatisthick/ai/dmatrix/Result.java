package soupthatisthick.ai.dmatrix;

public class Result<Action, Outcome> {
    public final Action action;
    public final Outcome outcome;

    public Result(final Action action, final Outcome outcome) {
        this.action = action;
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "[" + action.toString() + " => " + outcome.toString() + "]";
    }

}