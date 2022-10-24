package soupthatisthick.util.uniqueid;

import soupthatisthick.util.ifaces.comparison.Equatable;

import java.util.HashSet;
import java.util.Set;

public final class UniqueId implements Equatable {
    private static final Set<Integer> USED = new HashSet<>();
    private static Integer PREV = 0;

    private static int hash() {
        return (PREV==Integer.MAX_VALUE) ? Integer.MIN_VALUE : PREV + 1;
    }

    private final int value;

    public UniqueId() {
        synchronized(USED) {
            do {
                PREV = hash();
            } while (USED.contains(PREV));

            USED.add(PREV);
            this.value = PREV;
        }
    }


    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        return isEqualTo(other);
    }

    @Override
    public String toString() {
        return "UniqueId[" + value + "]";
    }

    @Override
    public final boolean isEqualTo(final Object other) {
        return isEquivalentTo(other);
    }

    @Override
    public boolean isEquivalentTo(final Object other) {
        if (other==null) {
            return false;
        } else if (other instanceof UniqueId) {
            return value == ((UniqueId) other).value;
        } else {
            return false;
        }
    }
}
