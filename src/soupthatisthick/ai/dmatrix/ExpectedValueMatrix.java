package soupthatisthick.ai.dmatrix;

public abstract class ExpectedValueMatrix<Action, Outcome> extends MeasurableOutcomeMatrix<Action, Outcome> {

    @Override
    public final double getResultMeasure(final Result<Action, Outcome> result) {
        return getResultProbability(result) * getResultValue(result);
    }

    public abstract double getResultProbability(Result<Action, Outcome> result);
    public abstract double getResultValue(Result<Action, Outcome> result);

}
