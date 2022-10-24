package soupthatisthick.var;

import org.junit.jupiter.api.Test;
import soupthatisthick.BaseTest;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class VarTest extends BaseTest {


    private StackTraceElement stackTraceElement;

    public void onSetup() {
    }

    public void onTearDown() {
    }

    @Test
    public void testSetup() {
        final Var v1 = new Var();

        assertNotNull(v1);
        assertTrue(v1.isAtomic());
        assertNull(v1.get());
        assertNotNull(v1.getProperties());
        assertTrue(v1.getProperties().isEmpty());
        assertNull(v1.get("does not exist"));
        assertTrue(v1.isEquivalentTo(null));
        assertEquals("null", v1.toString());
    }

    @Test
    public void equals() {
        final Var v1 = new Var();
        final Var v2 = new Var(v1);
        final Var v3 = new Var(v2);

        Logger.info(v1.toString("v1"));
        Logger.info(v2.toString("v2"));
        Logger.info(v3.toString("v3"));

        assertAllIsEquivelantTo("isEquivelantTo", v1, v2, v3);
    }

    @Test
    public void testCtors() {
        final byte theByte = 0x55;
        final short theShort = 0x4444;
        final char theChar = 'x';
        final int theInt = 12345;
        final long theLong = 9876543210L;
        final float theFloat = 3.14f;
        final double theDouble = 12345.678987654321d;
        final String theString = "theString";
        final boolean theBoolean = true;

        final Var vByte = new Var(theByte);
        final Var vShort = new Var(theShort);
        final Var vChar = new Var(theChar);
        final Var vInt = new Var(theInt);
        final Var vLong = new Var(theLong);
        final Var vFloat = new Var(theFloat);
        final Var vDouble = new Var(theDouble);
        final Var vString = new Var(theString);
        final Var vBoolean = new Var(theBoolean);

        // We can compare variables to other primatives, but the reverse is not necessarily true
        assertTrue(vByte.isEquivalentTo(theByte));
        assertTrue(vShort.isEquivalentTo(theShort));
        assertTrue(vChar.isEquivalentTo(theChar));
        assertTrue(vInt.isEquivalentTo(theInt));
        assertTrue(vLong.isEquivalentTo(theLong));
        assertTrue(vFloat.isEquivalentTo(theFloat));
        assertTrue(vDouble.isEquivalentTo(theDouble));
        assertTrue(vString.isEquivalentTo(theString));
        assertTrue(vBoolean.isEquivalentTo(theBoolean));

        assertFalse(vByte.isEqualTo(theByte));
        assertFalse(vShort.isEqualTo(theShort));
        assertFalse(vChar.isEqualTo(theChar));
        assertFalse(vInt.isEqualTo(theInt));
        assertFalse(vLong.isEqualTo(theLong));
        assertFalse(vFloat.isEqualTo(theFloat));
        assertFalse(vDouble.isEqualTo(theDouble));
        assertFalse(vString.isEqualTo(theString));
        assertFalse(vBoolean.isEqualTo(theBoolean));
    }


}
