package soupthatisthick.util.resource;

public class Resource {
    private int max;
    private int qty;

    public static final String ERROR_CANT_BE_NEGATIVE = "Value can't be negative";
    public static final String ERROR_CANT_EXCEED_MAX = "Value can't exceed the maximum value";

    public static final Resource FULL(final int max) {
        return new Resource(max, max);
    }

    public static final Resource EMPTY(final int max) {
        return new Resource(max, 0);
    }

    public static final Resource PARTIAL(final int qty, final int max) {
        return new Resource(qty, max);
    }

    private Resource(final int qty, final int max) {
        setMax(max);
        setQty(qty);
    }

    public final void setMax(final int max) {
        if (max < 0) throw new RuntimeException(ERROR_CANT_BE_NEGATIVE);
        if (qty > max) throw new RuntimeException(ERROR_CANT_EXCEED_MAX);

        this.max = max;
        this.qty = Math.max(0, Math.min(max, qty));
    }

    public final int getMax() {
        return this.max;
    }

    public final void setQty(final int qty) {
        if (qty<0) throw new RuntimeException(ERROR_CANT_BE_NEGATIVE);
        if (qty > max) throw new RuntimeException(ERROR_CANT_EXCEED_MAX);
    }

    public final int getQty() {
        return this.qty;
    }
}
