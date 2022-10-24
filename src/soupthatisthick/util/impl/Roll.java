package soupthatisthick.util.impl;

import java.util.Arrays;
import java.util.Random;

public class Roll {

    private static final Random R = new Random();

    public static int D20() { return die(20);   }
    public static int D12() { return die(12);   }
    public static int D10() { return die(10);   }
    public static int D8() { return die(8);     }
    public static int D6() { return die(6);     }
    public static int D4() { return die(4);     }
    public static int D2() { return die(2);     }

    public static boolean COIN_FLIP()   { return D2() == 1;   }

    public static int die(int size) {
        return 1 + R.nextInt(size);
    }

    public static int sum(int... values) {
        int sum = 0;
        for(int value : values) {
            sum += value;
        }
        return sum;
    }

    public static int max(int... values) {
        if (values.length<1) {
            throw new RuntimeException("There must be at least one value to determine the maximum from.");
        }
        int max = values[0];
        for(int i=1; i<values.length; i++) {
            if (values[i] > max) {
                values[i] = max;
            }
        }
        return max;
    }

    public static int min(int... values) {
        if (values.length<1) {
            throw new RuntimeException("There must be at least one value to determine the minimum from.");
        }
        int max = values[0];
        for(int i=1; i<values.length; i++) {
            if (values[i] < max) {
                values[i] = max;
            }
        }
        return max;
    }

    public static int[] dicePool(int... sizes) {
        int[] values = new int[sizes.length];
        for(int i=0; i<sizes.length; i++) {
            values[i] = die(sizes[i]);
        }
        return values;
    }

    public static int[] manyDie(int numDice, int dieSize) {
        int[] values = new int[numDice];
        for(int i=0; i<numDice; i++) {
            values[i] = die(dieSize);
        }
        return values;
    }

    /**
     * This will take an array of array of values and merge them into a single array of values.
     * The order added into the new array will be pool index then by index within the pool
     * @param pools is an {@link int[][]);}
     * @return an {@link int[]}
     */
    public static int[] merge(int[]... pools) {
        int newSize = 0;

        for(int[] pool : pools) {
            newSize += pool.length;
        }

        final int[] newPool = new int[newSize];
        int i=0;

        for(int[] pool : pools) {
            for(int value : pool) {
                newPool[i] = value;
                i++;
            }
        }
        return newPool;
    }

    public static int[] keepLowest(int numItems, int... items) {
        if (numItems > items.length) {
            throw new RuntimeException("There isn't " + numItems + " items to keep!");
        }
        Arrays.sort(items);
        final int[] keptItems = new int[numItems];
        for(int i=0; i<numItems; i++) {
            keptItems[i] = items[i];
        }
        return keptItems;
    }


    public static int[] keepHighest(int numItems, int... items) {
        if (numItems > items.length) {
            throw new RuntimeException("There isn't " + numItems + " items to keep!");
        }
        Arrays.sort(items);
        final int[] keptItems = new int[numItems];
        for(int i=0; i<numItems; i++) {
            keptItems[i] = items[items.length-i-1];
        }
        return keptItems;
    }

    public static double rollDecimal(double numDie, double dieSize) {
        if (numDie==0D || dieSize==0D) {
            return 0D;
        }
        double sum = 0;
        double qty = Math.abs(numDie);
        double theDie = Math.abs(dieSize);
        while(qty >= 1D) {
            sum += Math.random() * theDie;
            qty--;
        }
        sum += qty * Math.random() * theDie;
        sum *= numDie/qty * dieSize/theDie;
        return sum;
    }
}
