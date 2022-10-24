package soupthatisthick.alpha;

import org.junit.jupiter.api.Test;
import soupthatisthick.BaseTest;
import soupthatisthick.desc.Describe;
import soupthatisthick.util.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class AlphaTest extends BaseTest {
    private List<Alpha> alphaList;

    @Override
    protected void onSetup() {
        alphaList = new ArrayList<>();
    }

    @Override
    protected void onTearDown() {
        Logger.info(Describe.container(alphaList));
    }

    @Test
    public void testAlpha() {
         alphaList = Alpha.toAlphaList("The quick brown fox jumped over the lazy dog.", Alpha.SPACE);

         final String asString = Alpha.buildString(alphaList);
         Logger.info("\n" + asString);
    }
}
