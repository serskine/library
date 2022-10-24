package soupthatisthick.util.impls;

import org.junit.jupiter.api.Test;
import soupthatisthick.util.impl.Roll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RollTest {
    static final int[] VALUES = {0, 1, 2, 9, 7, 3, 8, 4, 6, 5};

    @Test
    public void keepHighest() {
        final int[] highest = Roll.keepHighest(3, VALUES);

        assertNotNull(highest);
        assertEquals(3, highest.length);
        assertEquals(9, highest[0]);
        assertEquals(8, highest[1]);
        assertEquals(7, highest[2]);

    }

    @Test
    public void keepLowest() {
        final int[] lowest = Roll.keepLowest(3, VALUES);

        assertNotNull(lowest);
        assertEquals(3, lowest.length);
        assertEquals(0, lowest[0]);
        assertEquals(1, lowest[1]);
        assertEquals(2, lowest[2]);
    }

}
