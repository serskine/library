package soupthatisthick.util.market;

import org.junit.jupiter.api.Test;
import soupthatisthick.BaseTest;
import soupthatisthick.desc.Describe;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarketTest extends BaseTest {

    private Market<String> market;

    @Override
    protected void onSetup() {
        market = new Market<>();
    }

    @Override
    protected void onTearDown() {
        Logger.info(Describe.map(market));
    }

    @Test
    public void testProbs() {
        final String a = "A";
        final String b = "B";
        final String c = "C";
        final String d = "D";
        final String e = "E";

        market.put(a, 1D);
        market.put(b, 2D);
        market.put(c, 3D);
        market.put(d, 4D);
        market.put(e, 0D);

        assertAllEquals(a, 0.1D, market.getProb(a));
        assertAllEquals(b, 0.2D, market.getProb(b));
        assertAllEquals(c, 0.3D, market.getProb(c));
        assertAllEquals(d, 0.4D, market.getProb(d));
        assertAllEquals(e, 0D, market.getProb(e));

        assertEquals(0.1D, market.getProb(a));
        assertEquals(0.2D, market.getProb(b));
        assertEquals(0.3D, market.getProb(c));
        assertEquals(0.4D, market.getProb(d));
        assertEquals(0D, market.getProb(e));

        assertEquals(10D, market.getSumWeight());

        assertEquals(5, market.size());

    }


}
