package soupthatisthick.util.impls.relations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.impl.relations.OneToManyImpl;
import soupthatisthick.util.impl.relations.OneToOneImpl;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static soupthatisthick.util.impls.relations.OneToManyTest.Target.*;
import static soupthatisthick.util.impls.relations.OneToManyTest.Target.T1;
import static soupthatisthick.util.impls.relations.OneToManyTest.Target.T2;
import static soupthatisthick.util.impls.relations.OneToManyTest.Target.T3;
import static soupthatisthick.util.impls.relations.OneToOneTest.Source.*;
import static soupthatisthick.util.impls.relations.OneToOneTest.Target.*;


public class OneToManyTest {


    public enum Source {
        S1, S2, S3
    }

    public enum Target {
        T1, T2, T3, T4, T5, T6
    }

    OneToMany relation = new OneToManyImpl();

    @BeforeEach
    public void onSetup() {
        relation = new OneToManyImpl();
    }


    @AfterEach
    public void onTearDown() {
        for(OneToOneTest.Source source : OneToOneTest.Source.values()) {
            for(OneToOneTest.Target target : OneToOneTest.Target.values()) {
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

        relation.link(S1, T1);
        relation.link(S1, T2);

        relation.link(S2, T3);
        relation.link(S2, T4);

        relation.link(S3, T5);
        relation.link(S3, T6);

        assertTrue(relation.isLink(S1, T1));
        assertTrue(relation.isLink(S1, T2));
        assertFalse(relation.isLink(S1, T3));
        assertFalse(relation.isLink(S1, T4));
        assertFalse(relation.isLink(S1, T5));
        assertFalse(relation.isLink(S1, T6));

        assertFalse(relation.isLink(S2, T1));
        assertFalse(relation.isLink(S2, T2));
        assertTrue(relation.isLink(S2, T3));
        assertTrue(relation.isLink(S2, T4));
        assertFalse(relation.isLink(S2, T5));
        assertFalse(relation.isLink(S2, T6));

        assertFalse(relation.isLink(S3, T1));
        assertFalse(relation.isLink(S3, T2));
        assertFalse(relation.isLink(S3, T3));
        assertFalse(relation.isLink(S3, T4));
        assertTrue(relation.isLink(S3, T5));
        assertTrue(relation.isLink(S3, T6));

        relation.unlink(S1, T1);


        assertFalse(relation.isLink(S1, T1));
        assertTrue(relation.isLink(S1, T2));
        assertFalse(relation.isLink(S1, T3));
        assertFalse(relation.isLink(S1, T4));
        assertFalse(relation.isLink(S1, T5));
        assertFalse(relation.isLink(S1, T6));

        assertFalse(relation.isLink(S2, T1));
        assertFalse(relation.isLink(S2, T2));
        assertTrue(relation.isLink(S2, T3));
        assertTrue(relation.isLink(S2, T4));
        assertFalse(relation.isLink(S2, T5));
        assertFalse(relation.isLink(S2, T6));

        assertFalse(relation.isLink(S3, T1));
        assertFalse(relation.isLink(S3, T2));
        assertFalse(relation.isLink(S3, T3));
        assertFalse(relation.isLink(S3, T4));
        assertTrue(relation.isLink(S3, T5));
        assertTrue(relation.isLink(S3, T6));


    }

    @Test
    public void addingTargetForSourceChangesTargetParent() {
        relation.link(S1, T1);
        relation.link(S1, T2);
        relation.link(S2, T3);

        assertTrue(relation.isLink(S1, T1));
        assertTrue(relation.isLink(S1, T2));
        assertFalse(relation.isLink(S1, T3));
        assertTrue(relation.isLink(S2, T3));

        assertEquals(S1, relation.getSourceOf(T1).get());
        assertEquals(S1, relation.getSourceOf(T2).get());
        assertEquals(S2, relation.getSourceOf(T3).get());

        relation.link(S1, T3);

        assertTrue(relation.isLink(S1, T1));
        assertTrue(relation.isLink(S1, T2));
        assertTrue(relation.isLink(S1, T3));
        assertFalse(relation.isLink(S2, T3));

        assertEquals(S1, relation.getSourceOf(T1).get());
        assertEquals(S1, relation.getSourceOf(T2).get());
        assertEquals(S1, relation.getSourceOf(T3).get());
    }



}
