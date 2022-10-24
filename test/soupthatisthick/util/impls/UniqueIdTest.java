package soupthatisthick.util.impls;

import org.junit.jupiter.api.Test;
import soupthatisthick.util.uniqueid.UniqueId;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UniqueIdTest {

    public static final int NUM_ID = 10;

    @Test
    public void makeAndFree() {
        final Set<UniqueId> theSet = new HashSet<>();
        final UniqueId[] ids = new UniqueId[NUM_ID];

        for(int i=0; i<NUM_ID; i++) {
            ids[i] = new UniqueId();
            theSet.add(ids[i]);
        }

        for(int i=0; i<NUM_ID; i++) {
            assertEquals(ids[i], ids[i]);

            for(int j=i+1; j<NUM_ID; j++) {
                assertNotEquals(ids[i], ids[j]);
            }
        }

        assertEquals(NUM_ID, theSet.size());
    }
}
