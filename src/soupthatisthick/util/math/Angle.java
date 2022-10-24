package soupthatisthick.util.math;

public class Angle {
    public static final double PI = Math.PI;
    public static final double TWO_PI = 2D * PI;

    private double value;
    private boolean isValueRadians;

    public Angle() {
        this(true, 0D);
    }

    public Angle(final Angle copyOf) {
        this.value = copyOf.value;
        this.isValueRadians = copyOf.isValueRadians;
    }

    public Angle(final boolean isValueRadians, double value) {
        this.isValueRadians = isValueRadians;
        this.value = value;
    }

    public double degrees() {
        return (isValueRadians) ? Math.toDegrees(value) : value;
    }

    public double radians() {
        return (isValueRadians) ? value : Math.toRadians(value);
    }

    public void setRadians(final double radians) {
        this.isValueRadians = true;
        this.value = radians;
    }

    public void setDegrees(final double degrees) {
        this.isValueRadians = false;
        this.value = degrees;
    }

    public Angle add(final Angle... angles) {
        double sumRadians = radians();
        for(Angle angle: angles) {
            sumRadians += angle.radians();
        }
        return new Angle(true, sumRadians);
    }

    public Angle inverse() {
        return new Angle(isValueRadians, -value);
    }

    public Angle subtract(final Angle angle) {
        return add(angle.inverse());
    }

    public Angle normalized() {
        final Angle a = new Angle(this);
        a.normalize();
        return a;
    }

    public Angle normalized(final Angle center) {
        final Angle a = new Angle(this);
        a.normalize(center);
        return a;
    }

    public void normalize() {
        normalize(new Angle(true, 0D));
    }

    public void normalize(final Angle center) {
        if (isValueRadians) {
            while(value >= center.radians() + PI) {
                value -= TWO_PI;
            }
            while(value < center.radians()-PI) {
                value += TWO_PI;
            }
        } else {
            while(value >= center.degrees() + 180D) {
                value -= 360D;
            }
            while(value < center.degrees()-180D) {
                value += 360D;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%3.1f deg", degrees());
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Angle)) {
            return false;
        } else {
            final Angle a = (Angle) other;
            if (isValueRadians) {
                return (value == a.radians());
            } else {
                return (value == a.degrees());
            }
        }
    }
}
