package soupthatisthick.util.math;

import org.junit.jupiter.api.Test;
import soupthatisthick.util.logger.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AngleTest {

    @Test
    public void valueSame() {

        for(int trial=0; trial < 10; trial++) {
            double value = Math.random() * 360D - 180D;
            final Angle a = new Angle(false, value);
            final double radians = a.radians();
            final double degrees = a.degrees();

            assertEquals(value, degrees);
            assertEquals(radians, Math.toRadians(degrees));

            Logger.info("Degrees Trial " + trial + ": " + a.toString());
        }

        for(int trial=0; trial < 10; trial++) {
            double value = (Math.random() * 2D * Math.PI) - Math.PI;
            final Angle a = new Angle(true, value);
            final double radians = a.radians();
            final double degrees = a.degrees();

            assertEquals(value, radians);
            assertEquals(degrees, Math.toDegrees(value));

            Logger.info("Radians Trial " + trial + ": " + a.toString());
        }

    }

    @Test
    public void add() {
        final Angle a1 = new Angle(false, 45);
        final Angle a2 = new Angle(false, 45);
        final Angle a3 = new Angle(false, 45);

        final Angle r1 = a1.add(a2, a3);
        final Angle r2 = a1.add(a2).add(a3);
        final Angle r3 = a2.add(a3).add(a1);

        assertEquals(r1, r2);
        assertEquals(r1, r3);
        assertEquals(r2, r3);
    }

    @Test
    public void equals() {
        final Angle a1 = new Angle(false, 180D);
        final Angle a2 = new Angle(true, a1.radians());
        final Angle a3 = new Angle(false, a2.degrees());

        assertEquals(a1, a2);
        assertEquals(a1, a3);
        assertEquals(a2, a3);
    }

    @Test
    public void normalizing() {
        final Angle a1 = new Angle(false, 360);
        final Angle b1 = new Angle(a1);
        b1.normalize();
        final Angle c1 = a1.normalized();


        Logger.info("a1: " + a1.toString());
        Logger.info("b1: " + b1.toString());
        Logger.info("c1: " + c1.toString());

        assertEquals(b1.degrees(), c1.degrees());
        assertEquals(0, b1.degrees());
        assertEquals(0, c1.degrees());


        final Angle a2 = new Angle(false, 270);
        final Angle b2 = new Angle(a2);
        b2.normalize();
        final Angle c2 = a2.normalized();


        Logger.info("a2: " + a2.toString());
        Logger.info("b2: " + b2.toString());
        Logger.info("c2: " + c2.toString());

        assertEquals(b2.degrees(), c2.degrees());
        assertEquals(-90D, b2.degrees());
        assertEquals(-90D, c2.degrees());


        final Angle a3 = new Angle(false, 450);
        final Angle b3 = new Angle(a3);
        b3.normalize();
        final Angle c3 = a3.normalized();


        Logger.info("a3: " + a3.toString());
        Logger.info("b3: " + b3.toString());
        Logger.info("c3: " + c3.toString());

        assertEquals(b3.degrees(), c3.degrees());
        assertEquals(90D, b3.degrees());
        assertEquals(90D, c3.degrees());
    }

    @Test
    public void normalizingWithCenter() {
        Angle center = new Angle(false,180);

        final Angle a = new Angle(false, 360);
        final Angle b = new Angle(false, 360 + 90);
        final Angle c = new Angle(false, 360 + 270);
        final Angle d = new Angle(false, 360 + 360);

        a.normalize(center);
        b.normalize(center);
        c.normalize(center);
        d.normalize(center);

        assertEquals(0, a.degrees());
        assertEquals(90, b.degrees());
        assertEquals(270, c.degrees());
        assertEquals(0, d.degrees());

        center = new Angle(false, 270);

        a.normalize(center);
        b.normalize(center);
        c.normalize(center);
        d.normalize(center);

        assertEquals(360, a.degrees());
        assertEquals(90, b.degrees());
        assertEquals(270, c.degrees());
        assertEquals(360, d.degrees());
    }
}



