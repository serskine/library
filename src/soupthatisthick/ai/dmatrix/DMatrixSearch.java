package soupthatisthick.ai.dmatrix;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class DMatrixSearch<Action, State> {

    public DecisionMatrix<Action, State> fringe;
    public Set<Result> visited = new HashSet<>();

    public DMatrixSearch(DecisionMatrix<Action, State> fringe) {
        this.fringe = fringe;
    }

    /**
     * This will attempt to expand the existing decisition matrix until it can be expanded no more.
     * @return true if we were able to expand
     */
    public Result<Action, State> expand() {
        final Optional<Action> actionOpt = chooseAction();
        if (actionOpt.isEmpty()) {
            throw new RuntimeException("Failed to expand search! No actions remain to be chosen!");
        }
        final Action action = actionOpt.get();
        final Set<State> possibilities = fringe.manyToMany.getTargetsOf(action);
        final Optional<State> stateOpt = assumeOutcomeForAction(action, possibilities);
        if (stateOpt.isEmpty()) {
            throw new RuntimeException("Failed to assume a resulting state for action " + action);
        }
        final State state = stateOpt.get();
        final Result<Action, State> result = new Result<>(action, state);

        visited.add(result);
        final DecisionMatrix<Action, State> children = getChildren(result.outcome);
        processChildren(result, children);

        return result;
    }

    public abstract DecisionMatrix<Action, State> getChildren(State state);
    public abstract Optional<Action> chooseAction();
    public abstract Optional<State> assumeOutcomeForAction(final Action action, final Set<State> possibilities);

    public void processChildren(Result<Action, State> result, DecisionMatrix<Action, State> children) {
        fringe.manyToMany.unlink(result.action, result.outcome);

        for(Action childAction : children.manyToMany.getSources()) {
            final Set<State> childStates = children.manyToMany.getTargetsOf(childAction);
            for(State childState : childStates) {
                final Result<Action, State> newResult = new Result<>(result.action, childState);
                addToFringe(newResult);
            }
        }
    }

    public void addToFringe(Result<Action, State> newResult) {
        fringe.manyToMany.link(newResult.action, newResult.outcome);
    }
}












