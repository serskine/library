package soupthatisthick;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.desc.Describe;
import soupthatisthick.util.ifaces.comparison.Equatable;
import soupthatisthick.util.logger.Logger;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseTest {

    private StackTraceElement stackTraceElement;

    protected abstract void onSetup();
    protected abstract void onTearDown();

    @BeforeEach
    private void beforeEach() {
        stackTraceElement = Logger.getStackTraceElement();
        Logger.info("*** BEGIN : " + stackTraceElement.getFileName() + "." + stackTraceElement.getMethodName());
        onSetup();
    }

    @AfterEach
    private void afterEach() {
        onTearDown();
        Logger.info("*** END : " + stackTraceElement.getFileName() + "." + stackTraceElement.getMethodName());
    }

    @Test
    public void testSetup() {
        Logger.info("Do nothing in the test");
    }

    protected static final void assertAllEquals(final String title, Object... items) {
        final String message = String.format("%s - items := %s", title, Describe.container(Arrays.stream(items).toList()));
        Logger.info(message);

        for(int i=0; i<items.length; i++) {
            for(int j=0; j<items.length; j++) {
                final Object left = items[i];
                final Object right = items[j];
                final Boolean observedResult = left.equals(right);
                Logger.info(String.format("%s - items[%d].equals(items[%d]) = %s", title, i, j, observedResult));
                assertTrue(observedResult);
                assertEquals(items[i], items[j]);
            }
        }
    }

    protected static final void assertAllIsEqualTo(final String title, Equatable... items) {
        for(int i=0; i<items.length; i++) {
            for(int j=0; j<items.length; j++) {
                final Boolean observedResult = items[i].isEqualTo(items[j]);
                Logger.info(String.format("%s - items[%d].isEqualTo(items[%d]) = %s", title, i, j, observedResult));
                assertTrue(observedResult);
            }
        }
    }

    protected static final void assertAllIsEquivelantTo(final String title, Equatable... items) {
        for(int i=0; i<items.length; i++) {
            for(int j=0; j<items.length; j++) {
                final Boolean observedResult = items[i].isEquivalentTo(items[j]);
                Logger.info(String.format("%s - items[%d].isEquivalentTo(items[%d]) = %s", title, i, j, observedResult));
                assertTrue(observedResult);
            }
        }
    }
}
