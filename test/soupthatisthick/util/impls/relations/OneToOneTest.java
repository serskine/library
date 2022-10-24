package soupthatisthick.util.impls.relations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.impl.relations.OneToManyImpl;
import soupthatisthick.util.impl.relations.OneToOneImpl;
import soupthatisthick.util.logger.Logger;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static soupthatisthick.util.impls.relations.OneToOneTest.Source.*;
import static soupthatisthick.util.impls.relations.OneToOneTest.Target.*;

public class OneToOneTest {

    public enum Source {
        S1, S2, S3
    }
    public enum Target {
        T1, T2, T3
    }

    private OneToOneImpl<Source, Target> relation;

    @BeforeEach
    public void onSetup() {
        relation = new OneToOneImpl<>();
    }


    @AfterEach
    public void onTearDown() {
        for(Source source : Source.values()) {
            for(Target target : Target.values()) {
                final boolean isLink = relation.isLink(source, target);
                Logger.info("Pair[" + source + ", " + target + "] => " + isLink);
            }
        }
    }

    @Test
    public void testSetup() {

    }

    @Test
    public void simpleCrud() {
        assertFalse(relation.isLink(S1, T1));

        relation.link(S1, T1);
        relation.link(S2, T2);
        relation.link(S3, T3);

        assertTrue(relation.isLink(S1, T1));
        assertFalse(relation.isLink(S1, T2));
        assertFalse(relation.isLink(S1, T3));

        assertFalse(relation.isLink(S2, T1));
        assertTrue(relation.isLink(S2, T2));
        assertFalse(relation.isLink(S2, T3));

        assertFalse(relation.isLink(S3, T1));
        assertFalse(relation.isLink(S3, T2));
        assertTrue(relation.isLink(S3, T3));

        assertEquals(3, relation.getSources().size());
        assertEquals(3, relation.getTargets().size());

        relation.unlink(S1, T1);

        assertFalse(relation.isLink(S1, T1));
        assertFalse(relation.isLink(S1, T2));
        assertFalse(relation.isLink(S1, T3));

        assertFalse(relation.isLink(S2, T1));
        assertTrue(relation.isLink(S2, T2));
        assertFalse(relation.isLink(S2, T3));

        assertFalse(relation.isLink(S3, T1));
        assertFalse(relation.isLink(S3, T2));
        assertTrue(relation.isLink(S3, T3));

        assertEquals(2, relation.getSources().size());
        assertEquals(2, relation.getTargets().size());

        assertFalse(relation.isLink(S1, T1));

    }

    @Test
    public void changeParentWhenTargetChanges() {

        relation.link(S1, T1);

        assertEquals(S1, relation.getSourceOf(T1).get());
        assertEquals(T1, relation.getTargetOf(S1).get());

        assertTrue(relation.isLink(S1, T1));
        assertFalse(relation.isLink(S1, T2));
        assertFalse(relation.isLink(S1, T3));

        relation.link(S1, T2);

        assertEquals(S1, relation.getSourceOf(T2).get());
        assertEquals(T2, relation.getTargetOf(S1).get());

        assertFalse(relation.isLink(S1, T1));
        assertTrue(relation.isLink(S1, T2));
        assertFalse(relation.isLink(S1, T3));

    }

    @Test
    public void changeTargetWhenParentChanges() {

        relation.link(S1, T1);

        assertEquals(S1, relation.getSourceOf(T1).get());
        assertEquals(T1, relation.getTargetOf(S1).get());

        assertTrue(relation.isLink(S1, T1));
        assertFalse(relation.isLink(S2, T1));
        assertFalse(relation.isLink(S3, T1));

        relation.link(S2, T1);

        assertEquals(S2, relation.getSourceOf(T1).get());
        assertEquals(T1, relation.getTargetOf(S2).get());

        assertFalse(relation.isLink(S1, T1));
        assertTrue(relation.isLink(S2, T1));
        assertFalse(relation.isLink(S3, T1));

    }
}
