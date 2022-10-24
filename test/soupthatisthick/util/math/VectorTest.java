package soupthatisthick.util.math;

import org.junit.jupiter.api.Test;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.*;


public class VectorTest {

    public static final double ALLOWED_MARGIN_OF_ERROR = 0D;  // Percent margin of error in calculations

    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 0, 0, 0D);
        Vector v2 = new Vector(1000, 0, 0, 1);

        double dotProduct = v1.dotProduct(v2);

        assertEquals(1000, dotProduct);
    }

    @Test
    public void equals() {
        Vector4D v0 = new Vector4D(1D, 1D, 1D, 1D);
        Vector4D v1 = new Vector4D(1D, 1D, 1D, 1D);
        Vector4D v2 = new Vector4D(0D, 1D, 1D, 1D);
        Vector4D v3 = new Vector4D(1D, 0D, 1D, 1D);
        Vector4D v4 = new Vector4D(1D, 1D, 0D, 1D);

        assertFalse(v0.equals(null));
        assertFalse(v0.equals("HELLO"));
        assertTrue(v0.equals(v0));
        assertTrue(v0.equals(v1));
        assertTrue(v1.equals(v0));

        assertFalse(v0.equals(v2));
        assertFalse(v0.equals(v3));
        assertFalse(v0.equals(v4));

    }

    @Test
    public void crossProductUnit() {
        final Vector v1 = new Vector(1, 0D, 0D);
        final Vector v2 = new Vector(0D, 1, 0D);
        final Vector v3 = new Vector(0D, 0D, 1D);

        final Vector c12 = v1.crossProduct(v2);
        final Vector c13 = v1.crossProduct(v3);

        final Angle a12 = c12.angleBetween(v1);
        final Angle a13 = c13.angleBetween(v2);

        Logger.info(a12.toString());
        Logger.info(a13.toString());
    }

    @Test
    public void crossProduct3D() {
        final Vector a = new Vector(3D, -3D, 1D);
        final Vector b = new Vector(4D, 9D, 2D);

        final Vector c = a.crossProduct(b);
        assertEquals(-15, c.cell[0]);
        assertEquals(-2, c.cell[1]);
        assertEquals(39, c.cell[2]);
    }

    @Test
    public void crossProduct4D() {
        final Vector v1 = new Vector(1, 0, 0, 0);
        final Vector v2 = new Vector(0, 1, 0, 0);
        final Vector v3 = new Vector(0, 0, 1, 0);
        final Vector v4 = new Vector(0, 0, 0, 1);

        final Vector c123 = v1.crossProduct(v2, v3);
        Logger.info("c123: " + c123.toString());

        assertEquals(0D, c123.cell[0]);
        assertEquals(-0D, c123.cell[1]);
        assertEquals(0D, c123.cell[2]);
        assertEquals(-1D, c123.cell[3]);

        final Vector c124 = v1.crossProduct(v2, v4);
        Logger.info("c124: " + c124.toString());

        assertEquals(0D, c124.cell[0]);
        assertEquals(-0D, c124.cell[1]);
        assertEquals(1D, c124.cell[2]);
        assertEquals(-0D, c124.cell[3]);


        final Vector c134 = v1.crossProduct(v3, v4);
        Logger.info("c134: " + c134.toString());

        assertEquals(0D, c134.cell[0]);
        assertEquals(-1D, c134.cell[1]);
        assertEquals(0D, c134.cell[2]);
        assertEquals(-0D, c134.cell[3]);


    }

    @Test
    public void angleBetween3D() {
        final Vector v1 = new Vector(1, 0, 0);
        final Vector v2 = new Vector(0, 1, 0);
        final Vector v3 = new Vector(0, 0, 1);

        final Vector v4 = new Vector(-1, 0, 0);
        final Vector v5 = new Vector(0, -1, 0);
        final Vector v6 = new Vector(0, 0, -1);

        final Angle a11 = v1.angleBetween(v1);
        final Angle a12 = v1.angleBetween(v2);
        final Angle a13 = v1.angleBetween(v3);
        final Angle a14 = v1.angleBetween(v4);
        final Angle a15 = v1.angleBetween(v5);
        final Angle a16 = v1.angleBetween(v6);

        final Angle a21 = v2.angleBetween(v1);
        final Angle a22 = v2.angleBetween(v2);
        final Angle a23 = v2.angleBetween(v3);
        final Angle a24 = v2.angleBetween(v4);
        final Angle a25 = v2.angleBetween(v5);
        final Angle a26 = v2.angleBetween(v6);

        final Angle a31 = v3.angleBetween(v1);
        final Angle a32 = v3.angleBetween(v2);
        final Angle a33 = v3.angleBetween(v3);
        final Angle a34 = v3.angleBetween(v4);
        final Angle a35 = v3.angleBetween(v5);
        final Angle a36 = v3.angleBetween(v6);

        final Angle a41 = v4.angleBetween(v1);
        final Angle a42 = v4.angleBetween(v2);
        final Angle a43 = v4.angleBetween(v3);
        final Angle a44 = v4.angleBetween(v4);
        final Angle a45 = v4.angleBetween(v5);
        final Angle a46 = v4.angleBetween(v6);

        final Angle a51 = v5.angleBetween(v1);
        final Angle a52 = v5.angleBetween(v2);
        final Angle a53 = v5.angleBetween(v3);
        final Angle a54 = v5.angleBetween(v4);
        final Angle a55 = v5.angleBetween(v5);
        final Angle a56 = v5.angleBetween(v6);

        final Angle a61 = v6.angleBetween(v1);
        final Angle a62 = v6.angleBetween(v2);
        final Angle a63 = v6.angleBetween(v3);
        final Angle a64 = v6.angleBetween(v4);
        final Angle a65 = v6.angleBetween(v5);
        final Angle a66 = v6.angleBetween(v6);


        Logger.info("a11: " + a11.toString());
        Logger.info("a12: " + a12.toString());
        Logger.info("a13: " + a13.toString());
        Logger.info("a14: " + a14.toString());
        Logger.info("a15: " + a15.toString());
        Logger.info("a16: " + a16.toString());

        Logger.info("a21: " + a21.toString());
        Logger.info("a22: " + a22.toString());
        Logger.info("a23: " + a23.toString());
        Logger.info("a24: " + a24.toString());
        Logger.info("a25: " + a25.toString());
        Logger.info("a26: " + a26.toString());

        Logger.info("a31: " + a31.toString());
        Logger.info("a32: " + a32.toString());
        Logger.info("a33: " + a33.toString());
        Logger.info("a34: " + a34.toString());
        Logger.info("a35: " + a35.toString());
        Logger.info("a36: " + a36.toString());

        Logger.info("a41: " + a41.toString());
        Logger.info("a42: " + a42.toString());
        Logger.info("a43: " + a43.toString());
        Logger.info("a44: " + a44.toString());
        Logger.info("a45: " + a45.toString());
        Logger.info("a46: " + a46.toString());

        Logger.info("a51: " + a51.toString());
        Logger.info("a52: " + a52.toString());
        Logger.info("a53: " + a53.toString());
        Logger.info("a54: " + a54.toString());
        Logger.info("a55: " + a55.toString());
        Logger.info("a56: " + a56.toString());

        Logger.info("a61: " + a61.toString());
        Logger.info("a62: " + a62.toString());
        Logger.info("a63: " + a63.toString());
        Logger.info("a64: " + a64.toString());
        Logger.info("a65: " + a65.toString());
        Logger.info("a66: " + a66.toString());

        assertEquals(0, a11.degrees());
        assertEquals(90, a12.degrees());
        assertEquals(90, a13.degrees());
        assertEquals(180, a14.degrees());
        assertEquals(90, a15.degrees());
        assertEquals(90, a16.degrees());

        assertEquals(90, a21.degrees());
        assertEquals(0, a22.degrees());
        assertEquals(90, a23.degrees());
        assertEquals(90, a24.degrees());
        assertEquals(180, a25.degrees());
        assertEquals(90, a26.degrees());

        assertEquals(90, a31.degrees());
        assertEquals(90, a32.degrees());
        assertEquals(0, a33.degrees());
        assertEquals(90, a34.degrees());
        assertEquals(90, a35.degrees());
        assertEquals(180, a36.degrees());

        assertEquals(180, a41.degrees());
        assertEquals(90, a42.degrees());
        assertEquals(90, a43.degrees());
        assertEquals(0, a44.degrees());
        assertEquals(90, a45.degrees());
        assertEquals(90, a46.degrees());

        assertEquals(90, a51.degrees());
        assertEquals(180, a52.degrees());
        assertEquals(90, a53.degrees());
        assertEquals(90, a54.degrees());
        assertEquals(0, a55.degrees());
        assertEquals(90, a56.degrees());

        assertEquals(90, a61.degrees());
        assertEquals(90, a62.degrees());
        assertEquals(180, a63.degrees());
        assertEquals(90, a64.degrees());
        assertEquals(90, a65.degrees());
        assertEquals(0, a66.degrees());



    }

    @Test
    public void angleBetween2D() {
        final Vector v1 = new Vector(1, 0);
        final Vector v2 = new Vector(0, 1);
        final Vector v3 = new Vector(-1, 0);
        final Vector v4 = new Vector(0, -1);

        final Angle a11 = v1.angleBetween(v1);
        final Angle a12 = v1.angleBetween(v2);
        final Angle a13 = v1.angleBetween(v3);
        final Angle a14 = v1.angleBetween(v4);

        final Angle a21 = v2.angleBetween(v1);
        final Angle a22 = v2.angleBetween(v2);
        final Angle a23 = v2.angleBetween(v3);
        final Angle a24 = v2.angleBetween(v4);

        final Angle a31 = v3.angleBetween(v1);
        final Angle a32 = v3.angleBetween(v2);
        final Angle a33 = v3.angleBetween(v3);
        final Angle a34 = v3.angleBetween(v4);

        final Angle a41 = v4.angleBetween(v1);
        final Angle a42 = v4.angleBetween(v2);
        final Angle a43 = v4.angleBetween(v3);
        final Angle a44 = v4.angleBetween(v4);

        Logger.info("a11: " + a11.toString());
        Logger.info("a12: " + a12.toString());
        Logger.info("a13: " + a13.toString());
        Logger.info("a14: " + a14.toString());

        Logger.info("a21: " + a21.toString());
        Logger.info("a22: " + a22.toString());
        Logger.info("a23: " + a23.toString());
        Logger.info("a24: " + a24.toString());

        Logger.info("a31: " + a31.toString());
        Logger.info("a32: " + a32.toString());
        Logger.info("a33: " + a33.toString());
        Logger.info("a34: " + a34.toString());

        Logger.info("a41: " + a41.toString());
        Logger.info("a42: " + a42.toString());
        Logger.info("a43: " + a43.toString());
        Logger.info("a44: " + a44.toString());


        assertEquals(0, a11.degrees());
        assertEquals(90, a12.degrees());
        assertEquals(180, a13.degrees());
        assertEquals(90, a14.degrees());

        assertEquals(90, a21.degrees());
        assertEquals(0, a22.degrees());
        assertEquals(90, a23.degrees());
        assertEquals(180, a24.degrees());

        assertEquals(180, a31.degrees());
        assertEquals(90, a32.degrees());
        assertEquals(0, a33.degrees());
        assertEquals(90, a34.degrees());

        assertEquals(90, a41.degrees());
        assertEquals(180, a42.degrees());
        assertEquals(90, a43.degrees());
        assertEquals(0, a44.degrees());

    }


    void assertSame(Vector expected, Vector observed) {
        if (expected==null) {
            assertNull(observed);
        } else {
            assertClose(expected.numDimensions, observed.numDimensions);
            for(int i=0; i<expected.numDimensions; i++) {
                assertClose(expected.cell[i], observed.cell[i]);
            }
            boolean opResult = expected.equals(observed);
            assertTrue(opResult);
        }
    }

    public void assertClose(double expected, double observed) {
        double diff = Math.abs(expected-observed);
        if (diff > ALLOWED_MARGIN_OF_ERROR) {
            throw new RuntimeException("observed [" + observed + "] but expected [" + expected + "] and its not within the margin of error [" + ALLOWED_MARGIN_OF_ERROR + "]. Diff is [" + diff + "]");
        }
    }
}
