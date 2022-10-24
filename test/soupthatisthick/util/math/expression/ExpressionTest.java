package soupthatisthick.util.math.expression;

import org.junit.jupiter.api.Test;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ExpressionTest {

    public double[] expected = {
            1D,
            -1D,
            0D,
            7D
    };
    public String[] expr = {
            "1 -2 + 2",
            "-2 + 7 - 6",
            "9 * 9 * 0",
            "1 + 4*3/(1+1)"
    };


    @Test
    public void exprTest() {
        for(int i=0; i<expected.length; i++) {
            try {
                final double observed = Expression.eval(String.valueOf(expr[i]));
                final String expectedText = "Expr[" + i + "]: " + expected[i];
                final String observedText = "Expr[" + i + "]: " + observed;
                assertEquals(expectedText, observedText);
            } catch (Exception e) {
                final String message = "Expression[" + i + "]: " + e.getMessage();
                fail(message);
            }
        }
    }

    @Test
    public void exprWithRandom() {
        final double result = Expression.eval("d20");
        Logger.info("Result = " + result);
    }
}
