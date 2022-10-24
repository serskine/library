package soupthatisthick.ai.dmatrix;

import soupthatisthick.util.Util;
import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.impl.relations.ManyToManyImpl;

import java.util.Comparator;
import java.util.Optional;

public abstract class DecisionMatrix<Action, Outcome> {
    public final ManyToMany<Action, Outcome> manyToMany = new ManyToManyImpl<>();

    public final Optional<Action> getBestAction() {
        return Util.chooseRandomly(Util.getBestOf(manyToMany.getSources(), getActionComparator()));
    }

    protected abstract Comparator<Action> getActionComparator();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append("\n*** DECISION MATRIX ***\n");
        for(Action action : manyToMany.getSources()) {
            sb.append(" - " + action.toString() + "\n");
            for(Outcome outcome : manyToMany.getTargetsOf(action)) {
                sb.append("   => " + outcome.toString() + "\n");
            }
        }
        sb.append("***********************\n");

        return sb.toString();
    }
}

