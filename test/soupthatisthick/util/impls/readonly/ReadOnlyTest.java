package soupthatisthick.util.impls.readonly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.ifaces.relations.OneToOne;
import soupthatisthick.util.impl.readonly.ReadOnly;
import soupthatisthick.util.impl.relations.ManyToManyImpl;
import soupthatisthick.util.impl.relations.OneToManyImpl;
import soupthatisthick.util.impl.relations.OneToOneImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReadOnlyTest {

    public static final String ERROR_EXPECTED_EXCEPTION = "Expected an exception to be thrown but one wasn't";

    @BeforeEach
    public void onSetup() {

    }

    @Test
    public void set() {
        final Set<String> theContainer = ReadOnly.set(new HashSet<>());
        try {
            theContainer.add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.remove("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.clear();
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }


        try {
            theContainer.addAll(new ArrayList<String>());
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.removeAll(new ArrayList<String>());
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }


        try {
            theContainer.retainAll(new ArrayList<String>());
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

    }

    @Test
    public void collection() {
        final Collection<String> theContainer = ReadOnly.collection(new ArrayList<>());
        try {
            theContainer.add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.remove("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.clear();
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.addAll(new ArrayList<String>());
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.removeAll(new ArrayList<String>());
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.retainAll(new ArrayList<String>());
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.clear();
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

    }

    @Test
    public void map() {
        final Map<String, Integer> theContainer = ReadOnly.map(new HashMap<>());
        try {
            theContainer.put("X", 99);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.remove("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.clear();
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.putAll(new HashMap<String, Integer>());
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.clear();
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

    }

    @Test
    public void oneToOne() {
        final OneToOne<String, Integer> theContainer = ReadOnly.oneToOne(new OneToOneImpl<>());

        try {
            theContainer.link("A", 1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.unlink("A", 1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getSources().add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }


        try {
            theContainer.getTargets().add(1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getTargetsOf("A").add(1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getSourcesOf(1).add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.clear();
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

    }

    @Test
    public void oneToMany() {
        final OneToMany<String, Integer> theContainer = ReadOnly.oneToMany(new OneToManyImpl<>());

        try {
            theContainer.link("A", 1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.unlink("A", 1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getSources().add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }


        try {
            theContainer.getTargets().add(1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getTargetsOf("A").add(1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getSourcesOf(1).add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.clear();
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }
    }


    @Test
    public void manyToMany() {
        final ManyToMany<String, Integer> theContainer = ReadOnly.manyToMany(new ManyToManyImpl<>());

        try {
            theContainer.link("A", 1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.unlink("A", 1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getSources().add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }


        try {
            theContainer.getTargets().add(1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getTargetsOf("A").add(1);
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }

        try {
            theContainer.getSourcesOf(1).add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            assertNotEquals(ERROR_EXPECTED_EXCEPTION, e.getMessage());
        }
    }
}
