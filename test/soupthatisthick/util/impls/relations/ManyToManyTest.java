package soupthatisthick.util.impls.relations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.impl.relations.ManyToManyImpl;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static soupthatisthick.util.impls.relations.ManyToManyTest.Source.*;
import static soupthatisthick.util.impls.relations.ManyToManyTest.Target.*;

public class ManyToManyTest {

    public enum Source {
        S1, S2, S3
    }

    public enum Target {
        T1, T2, T3
    }

    private ManyToMany<Source, Target> relation = new ManyToManyImpl<>();

    @BeforeEach
    public void onSetup() {
        relation = new ManyToManyImpl<>();
    }


    @AfterEach
    public void onTearDown() {
        for(ManyToManyTest.Source source : ManyToManyTest.Source.values()) {
            for(ManyToManyTest.Target target : ManyToManyTest.Target.values()) {
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

        boolean observed = relation.isLink(S1, T1);
        assertTrue(observed);

        relation.unlink(S1, T1);

        observed = relation.isLink(S1, T1);
        assertFalse(observed);

    }

    @Test
    public void crud() {
        relation.link(S1, T2);
        relation.link(S1, T3);
//        relation.link(S2, T1);
//        relation.link(S2, T3);
//        relation.link(S3, T1);
//        relation.link(S3, T2);

        assertFalse(relation.isLink(S1, T1));
        assertTrue(relation.isLink(S1, T2));
        assertTrue(relation.isLink(S1, T3));

//        assertTrue(relation.isLink(S2, T1));
//        assertFalse(relation.isLink(S2, T2));
//        assertTrue(relation.isLink(S2, T3));
//
//        assertTrue(relation.isLink(S3, T1));
//        assertTrue(relation.isLink(S3, T2));
//        assertFalse(relation.isLink(S3, T3));

//        relation.unlink(S1, T2);
//
//        assertFalse(relation.isLink(S1, T1));
//        assertFalse(relation.isLink(S1, T2));
//        assertTrue(relation.isLink(S1, T3));
//
//        assertTrue(relation.isLink(S2, T1));
//        assertFalse(relation.isLink(S2, T2));
//        assertTrue(relation.isLink(S2, T3));
//
//        assertTrue(relation.isLink(S3, T1));
//        assertTrue(relation.isLink(S3, T2));
//        assertFalse(relation.isLink(S3, T3));

    }

}
