package soupthatisthick.dynamic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.logger.Logger;
import soupthatisthick.util.uniqueid.UniqueId;
import soupthatisthick.var.Var;

import static org.junit.jupiter.api.Assertions.*;

public class VarTest {

    private Var v0, v1, v2, v3;


    @BeforeEach
    public void onSetup() {
        v0 = new Var();
        v1 = new Var("Hello");
        v2 = new Var();
        v3 = new Var();

        v2.set("p1", v0);
        v2.set("p2", v1);
        v2.set("next", v3);

        v3.set("name", "Stuart");
        v3.set("id", new UniqueId());
        v3.set("next", v3);

    }

    @AfterEach
    public void onTeardown() {
        Logger.info(v0.toString("v0"));
        Logger.info(v1.toString("v1"));
        Logger.info(v2.toString("v2"));
        Logger.info(v3.toString("v3"));
    }

    @Test
    public void testSetup() {

    }

    @Test
    public void testVariable() {
        assertNotNull(v1);
        assertTrue(v1.isAtomic());
        assertTrue(v1.getProperties().isEmpty());

        assertNotNull(v2);
        assertFalse(v2.isAtomic());
        assertFalse(v2.getProperties().isEmpty());


    }


}





