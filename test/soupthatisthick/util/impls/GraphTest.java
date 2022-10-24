package soupthatisthick.util.impls;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.impl.Graph;
import soupthatisthick.util.logger.Logger;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static soupthatisthick.util.impls.GraphTest.Vertex.V1;
import static soupthatisthick.util.impls.GraphTest.Vertex.V2;

public class GraphTest {

    public static final int NO_VALUE = -1;

    public enum Vertex {
        V1, V2, V3, V4, V5, V6
    }

    public enum Link {
        HAS_LINK, NO_PATH
    }

    private Graph<Vertex, Link> g;

    @BeforeEach
    public void onSetup() {
        Logger.IS_DEBUG = true;
        g = new Graph<Vertex, Link>();

        for(int i=0; i<Vertex.values().length-1; i++) {
            final Vertex source = Vertex.values()[i];
            for(int j=i+1; j<Vertex.values().length; j++) {
                final Vertex target = Vertex.values()[j];
                g.link(source, target, Link.HAS_LINK);
            }
        }
    }

    @AfterEach
    public void onTearDown() {
        for(Vertex source : Vertex.values()) {
            for(Vertex target : Vertex.values()) {
                Link link = g.getLinkOrDefault(source, target, Link.NO_PATH);
                Logger.info("" + source + " => " + target + " = " + link);
            }
        }
    }

    private String vertexName(int i) {
        return "V[" + i + "]";
    }

    @Test
    public void testSetup() {
        assertNotNull(g);
    }

    @Test
    public void link() {

        g.link(V1, V2, Link.HAS_LINK);
        final boolean hasLink = g.hasLink(V1, V2);
        assertTrue(hasLink);

    }

    @Test
    public void linkToMany() {
        g.link(V1, V1, Link.HAS_LINK);
        g.link(V1, V2, Link.HAS_LINK);

        final boolean hasLink1 = g.hasLink(V1, V1);
        final boolean hasLink2 = g.hasLink(V1, V2);

        assertTrue(hasLink1);
        assertTrue(hasLink2);

        assertFalse(g.hasLink(V2, V1));
    }

}
