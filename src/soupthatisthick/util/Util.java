package soupthatisthick.util;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import soupthatisthick.util.logger.Logger;

public class Util {

    public static final long MS_PER_SECOND = 1000L;
    public static final long NANO_PER_MS = 1000000L;
    public static final long NANO_PER_SECOND = MS_PER_SECOND * NANO_PER_MS;
    public static final Random R = new Random();

    public static <T> Optional<T> removeRandomly(List<T> items) {
        if (items.isEmpty()) {
            return Optional.empty();
        } else {
            int idx = (int) (Math.random() * items.size());
            return Optional.of(items.remove(idx));
        }
    }

    public static <T> Optional<T> chooseRandomly(List<T> items) {
        if (items.isEmpty()) {
            return Optional.empty();
        } else {
            int idx = (int) (Math.random() * items.size());
            return Optional.of(items.get(idx));
        }
    }

    public static <T>  Optional<T> chooseRandomly(final T... items) {
        if (items==null || items.length<1) {
            return Optional.empty();
        } else {
            int idx = (int) (Math.random() * items.length);
            return Optional.of(items[idx]);
        }
    }

    public static <T> Optional<T> removeFirstMatch(final Collection<T> items, final Function<T, Boolean> isMatch) {
        final Iterator<T> iterator =  items.iterator();
        while(iterator.hasNext()) {
            T item = iterator.next();
            final boolean isMatchResult = isMatch.apply(item);
            if (isMatchResult) {
                iterator.remove();
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public static <T> List<T> getBestOf(final Collection<T> items, final Comparator<T> comparator) {
        final List<T> theBest = new ArrayList<>();
        T best = null;
        for(T item : items) {
            if (best==null) {
                best = item;
            }
            final int cmp = comparator.compare(best, item);
            if (cmp < 0) {
                theBest.clear();
                best = item;
            }
            if (cmp <= 0) {
                theBest.add(item);
            }
        }
        return theBest;
    }


    public static <T> List<T> getWorstOf(final Collection<T> items, final Comparator<T> comparator) {
        final List<T> theWorst = new ArrayList<>();
        T worst = null;
        for(T item : items) {
            if (worst==null) {
                worst = item;
            }
            final int cmp = comparator.compare(worst, item);
            if (cmp > 0) {
                theWorst.clear();
                worst = item;
            }
            if (cmp <= 0) {
                theWorst.add(item);
            }
        }
        return theWorst;
    }

    /**
     * This will return al list of lists that gives all possible orderings of the provided items
     * @param items
     * @param <T>
     * @return
     */
    public static <T> Set<List<T>> allOrderingsOf(final List<T> items) {

        if (items.isEmpty()) {
            Set<List<T>> theSet = new HashSet<>();
            theSet.add(new ArrayList<>());
            return theSet;
        }

        final Set<List<T>> theSet = new HashSet<>();
        for(T item : items) {
            List<T> remainder = new ArrayList<>();
            remainder.addAll(items);
            remainder.remove(item);

            Set<List<T>> orderingsOfRemainder = allOrderingsOf(remainder);

            for(List<T> ordering : orderingsOfRemainder) {
                final List<T> newList = new ArrayList<>();
                newList.add(item);
                newList.addAll(ordering);
                theSet.add(newList);
            }
        }
        return theSet;
    }

    public static <T> Set<List<T>> allUniqueSetsOf(final List<T> items) {
        Set<List<T>> theSet = new HashSet<>();

        if (items.isEmpty()) {
            theSet.add(new ArrayList<>());
        } else {
            final List<T> front = items.subList(0, 1);
            final List<T> rear = items.subList(1, items.size());
            final Set<List<T>> rearCombinations = allUniqueSetsOf(rear);
            theSet.addAll(rearCombinations);
            for(List<T> remainder : rearCombinations) {
                List<T> toAdd = new ArrayList<>();
                toAdd.addAll(front);
                toAdd.addAll(remainder);
                theSet.add(toAdd);
            }
        }

        return theSet;
    }

    public static <T> Set<List<T>> allCombinationsOf(final List<T> items) {
        Set<List<T>> theSet = new HashSet<>();

        if (items.isEmpty()) {
            theSet.add(new ArrayList<>());
        } else {
            for(T item : items) {
                List<T> remainder = new ArrayList<>();
                remainder.addAll(items);
                remainder.remove(item);
                Set<List<T>> combos = allCombinationsOf(remainder);
                theSet.addAll(combos);
                theSet.add(remainder);
                for(List<T> remainderList : combos) {
                    final List<T> withRemainder = new ArrayList<>();
                    withRemainder.add(item);
                    withRemainder.addAll(remainderList);
                    theSet.add(withRemainder);
                }


            }
        }


        return theSet;
    }

    public static <T> String describeSet(Set<T> items) { return describeSet(items, true);   }
    public static <T> String describeSet(Set<T> items, boolean isOneLine) {
        final List<T> itemsList = items.stream().collect(Collectors.toList());
        return describeList(itemsList, isOneLine);
    }

    public static <T> String describeList(List<T> items) { return describeList(items, true);   }
    public static <T> String describeList(List<T> items, boolean isOneLine) {
        StringBuilder sb = new StringBuilder();
        if (items==null) {
            sb.append("null");
        } else {
            sb.append("{");
            if (!isOneLine) {
                sb.append("\n\t");
            }
            for (int i = 0; i < items.size(); i++) {
                if (i > 0) {
                    sb.append(",");
                    if (isOneLine) {
                        sb.append(" ");
                    } else {
                        sb.append("\n\t");
                    }
                }

                sb.append(items.get(i));
            }
            if (!isOneLine) {
                sb.append("\n");
            }
            sb.append("}");
        }
        return sb.toString();
    }

    public static final String describeObject(Object obj) {
        if (obj==null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    public static <T> List<T> tournament(List<T> items, BiFunction<T, T, Boolean> runGame) {


        if (items.isEmpty()) {
            return items;
        } else if (items.size()==1) {
            return items;
        } else {

            List<T> results = new ArrayList<>();

            final List<T>[] divisions = split(2, items);

            final List<T>[] divisionResults = new List[divisions.length];
            for(int i=0; i< divisions.length; i++) {
                divisionResults[i] = tournament(divisions[i], runGame);
            }

            final List<T> divisionA = divisionResults[0];
            final List<T> divisionB = divisionResults[1];

            boolean done = divisionA.isEmpty() || divisionB.isEmpty();
            while(!done) {
                T a = divisionA.remove(0);
                T b = divisionB.remove(0);

                if (runGame.apply(a, b)) {
                    results.add(a);
                    results.add(b);
                } else {
                    results.add(b);
                    results.add(a);
                }
                done = divisionA.isEmpty() || divisionB.isEmpty();
            }

            while(!divisionA.isEmpty()) {
                results.add(divisionA.remove(0));
            }


            while(!divisionB.isEmpty()) {
                results.add(divisionB.remove(0));
            }

            return results;
        }
    }

    public static <T> List<T>[] split(int numLists, T... items) {
        return split(numLists, Arrays.asList(items));
    }

    public static <T> List<T>[] split(final int numLists, List<T> items) {
        if (items.size() < numLists) {
            throw new RuntimeException("There are not enout items in the list!");
        }

        List<T> lists[] = new List[numLists];
        for(int i=0; i<lists.length; i++) {
            lists[i] = new ArrayList<>();
        }

        for(int i=0; i<items.size(); i++) {
            lists[i%numLists].add(items.get(i));
        }

        return lists;
    }

    public static <T> Map<String, List<T>> createMappings(Collection<List<T>> combos) {
        final Map<String, List<T>> mapping = new HashMap<>();
        for(List<T> combo : combos) {
            mapping.put(describeList(combo), combo);
        }
        return mapping;
    }

    public static <T> Set<List<T>> createSetOfLists(Collection<T> items) {
        final Set<List<T>> orderSet = new HashSet<>();
        for(T item : items) {
            List<T> list = new ArrayList<>();
            list.add(item);
            orderSet.add(list);
        }
        return orderSet;
    }

    public static <T> List<T> reverse(List<T> items) {
        LinkedList<T> order = new LinkedList<>();
        for(T item : items) {
            order.offerFirst(item);
        }
        return order;
    }

    /**
     * This will check the contents of two collections to see if their contents are identical in quantity only.
     * This does not care about the ordering of the items
     * @param left
     * @param right
     * @param <T>
     * @return true if equivelent without ordering and false if either collection contained an item the other did not.
     */
    public static <T> boolean isEquivelentUnorderedItems(final Collection<T> left, final Collection<T> right) {
        final List<T> leftList = new ArrayList<>();
        final List<T> rightList = new ArrayList<>();

        leftList.addAll(left);
        rightList.addAll(right);

        if (leftList.size() != right.size()) {
            return false;   // Shortcut. The two lists must be the same size to work.
        }

        while(!leftList.isEmpty() && !rightList.isEmpty()) {
            final Optional<T> itemOpt = removeRandomly(leftList);
            if (itemOpt.isEmpty()) {
                return false;   // The right list has items the left list does not contain
            } else {
                T item = itemOpt.get();
                final boolean wasPresent = rightList.remove(item);
                if (!wasPresent) {
                    return false;   // The right list did not contain an item found in the left list
                }
            }
        }

        return true;    // There is nothing left in both lists. There are equivelent. (Without ordering)
    }

    public static <T> boolean isEquivelentUnorderedItems(
            final Collection<T> left,
            final Collection<T> right,
            final BiFunction<T, T, Boolean> isItemSame
    ) {
        final LinkedList<T> leftList = new LinkedList<>();
        final LinkedList<T> rightList = new LinkedList<>();

        leftList.addAll(left);
        rightList.addAll(right);

        if (leftList.size() != right.size()) {
            return false;   // Shortcut. The two lists must be the same size to work.
        }

        while(!leftList.isEmpty() && !rightList.isEmpty()) {
            final T leftItem = leftList.removeFirst();
            Optional<T> rightItem = removeFirstMatch(rightList, x -> isItemSame.apply(leftItem, x));
            if (!rightItem.isPresent()) {
                return false;   // There is an item the first list had the second did not.
            }
        }

        return true;    // There is nothing left in both lists. There are equivelent. (Without ordering)
    }




    public static <T> Set<T> removeDuplicates(Collection<T> items, BiFunction<T, T, Boolean> isSameFunction) {
        final Set<T> keptItems = new HashSet<>();
        for(T item : items) {
            boolean toKeep = true;
            for(T keptItem : keptItems) {
                if (isSameFunction.apply(item, keptItem)) {
                    toKeep = false;
                    break;
                }
            }
            if (toKeep) {
                keptItems.add(item);
            }
        }
        return keptItems;
    }

    public static void pause(int milliSeconds) {
        if (milliSeconds > 0) {
            try {
                Thread.sleep(milliSeconds);
            } catch (Exception e) {
                Logger.error(e);
            }
        }
    }

    public static void waitUntil(Supplier<Boolean> condition) {
        while(!condition.get()) {
            pause(500); // Pause the thread for a bit
        }
    }

    public static Color getClearerColor(final Color color) {
        return getAlphaColor(color, color.getAlpha()/2);
    }


    public static Color getAlphaColor(final Color color, double alphaP) {
        alphaP = Math.max(0, Math.min(1D, alphaP));
        final int alpha = (int) (255D * alphaP);
        final int red = color.getRed();
        final int green = color.getGreen();
        final int blue = color.getBlue();
        return new Color(red, green, blue, alpha);
    }

    public static int toRange(int value, int min, int max) {
        if (max==min) {
            throw new RuntimeException("The maximum value must be greater than the minimum value!");
        } else if (max<min) {
            return toRange(value, max, min);
        } else if (value < min) {
            return toRange(value + (max-min), min, max);
        } else if (value >= max) {
            return toRange(value - (max-min), min, max);
        } else {
            return value;
        }
    }

    public static double toRangeDouble(double value, double min, double max) {
        if (max==min) {
            throw new RuntimeException("The maximum value must be greater than the minimum value!");
        } else if (max<min) {
            return toRangeDouble(value, max, min);
        } else if (value < min) {
            return toRangeDouble(value + (max-min), min, max);
        } else if (value >= max) {
            return toRangeDouble(value - (max-min), min, max);
        } else {
            return value;
        }
    }

    public static Rectangle getInnerBox(Rectangle r) {
        int boxSize = Math.min(r.width, r.height);
        int centerX = r.x + r.width/2;
        int centerY = r.y + r.height/2;
        return new Rectangle(
                centerX-boxSize/2,
                centerY-boxSize/2,
                boxSize,
                boxSize
        );
    }

    public static Rectangle getOuterBox(Rectangle r) {
        int boxSize = Math.max(r.width, r.height);
        int centerX = r.x + r.width/2;
        int centerY = r.y + r.height/2;
        return new Rectangle(
                centerX-boxSize/2,
                centerY-boxSize/2,
                boxSize,
                boxSize
        );
    }

    public static final Rectangle getOuterRectangle(Rectangle r, int margin) {
        if (margin<0) {
            return getInnerRectangle(r, -margin);
        }
        return new Rectangle(
                r.x-margin,
                r.y-margin,
                r.x+r.width+margin*2,
                r.y+r.height+margin*2
        );
    }

    public static Rectangle getInnerRectangle(Rectangle r, int margin) {
        if (margin<0) {
            return getOuterRectangle(r, -margin);
        }

        return new Rectangle(
                r.x + (Math.min(margin, r.width/2)),
                r.y + (Math.min(margin, r.height/2)),
                Math.max(0, r.width-(2*margin)),
                Math.max(0, r.height-(2*margin))
        );
    }

    public static final int MARGIN = 50;

    public static final Point getCenter(Rectangle r) {
        return new Point(r.x + r.width/2, r.y + r.height/2);
    }

    public static final List<String> componentPath(Component c) {

        if (c==null) {
            return new ArrayList<>();
        } else {
            final List<String> path = componentPath(c.getParent());
            path.add(c.getName());
            return path;
        }
    }

    public static final <T> void assertExpected(String message, T expected, T observed) {
        if (!Objects.equals(expected, observed)) {
            final String errorMessage = String.format("ERROR: %s\nExpected [%s] but was [%s] instead", message, expected, observed);
            throw new RuntimeException(errorMessage);
        }
    }

    public static final void assertError(String message, Exception e) {
        throw new RuntimeException("ERROR: " + message, e);
    }

    public static <T> List<T> cutRiffleMerge(final Collection<T> source) {
        final List<T> front = new ArrayList<>();
        final List<T> rear = new ArrayList<>();
        final int cut = R.nextInt(source.size());
        cut(source, cut, front, rear);
        return riffleMerge(front, rear);
    }

    /**
     * This will split the items into three piles
     * @param source contains all the items to split up
     * @param commonPile contains 3/6 of the items and gets the 1st, 4th and 6th placement of every 6 placements
     * @param uncommonPile contains 2/6 of the items and gets the 2nd and 5th of every 6 placements
     * @param rarePile contains 1/3 of the items and gets the 3rd placement of every 6 placements
     * @param <T>
     */
    public static <T> void placePiles(
            final Collection<T> source,
            final Collection<T> commonPile,
            final Collection<T> uncommonPile,
            final Collection<T> rarePile) {
        int c = 0;

        for(T item : source) {
            if (c%6 < 3) {
                commonPile.add(item);
            } else if (c%6 < 5) {
                uncommonPile.add(item);
            } else {
                rarePile.add(item);
            }
            c = (c+1)%6;
        }
    }

    /**
     * This will return an array of piles. It will distribute the items in the source collection
     * evenly amongst the piles if possible. It will iterate over each pile until one item has been placed in each.
     * In the case the items cann't be distributed evenly, all the smaller piles will be at the rear.
     * @param source contains all the items to distribute
     * @param numPiles is the number of piles we want
     * @param <T>
     * @return an array of piles with items distributed evenly amongst them.
     */
    public static <T> List<T>[] splitPiles(final Collection<T> source, int numPiles) {
        final List<T>[] piles = new List[numPiles];
        for(int i=0; i<piles.length; i++) {
            piles[i] = new ArrayList<T>();
        }
        int i=0;
        for(T item : source) {
            piles[i].add(item);
            i = (i + 1) % piles.length;
        }
        return piles;
    }

    /**
     * This will return a {@link LinkedList<T>} with the items reversed
     * @param source is the original collection of items
     * @param <T>
     * @return the items in reversed order
     */
    public static <T> List<T> reversePile(final Collection<T> source) {
        final LinkedList<T> items = new LinkedList<>();
        for(T item : source) {
            items.addFirst(item);
        };
        return items;
    }

    public static <T> void cut(final Collection<T> source, final int cut, final Collection<T> front, final Collection<T> rear) {
        final Iterator<T> itr = source.iterator();
        int c = 0;
        while(itr.hasNext()) {
            if (c<cut) {
                front.add(itr.next());
            } else {
                rear.add(itr.next());
            }
        }
    }

    public static <T> List<T> cutShuffle(final Collection<T> a) {
        final List<T> rear = new ArrayList<>();
        final List<T> front = new ArrayList<>();

        cut(a, R.nextInt(a.size()/2), front, rear);

        rear.addAll(front); // Adds the front items to the rear
        return rear;
    }

    public static <T> List<T> merge(final Collection<T> a, Collection<T> b) {
        final List<T> items = new ArrayList<>();
        final Iterator<T> aItr = a.iterator();
        final Iterator<T> bItr = b.iterator();


        // Iterate over both lists randomly choosing one from each
        boolean isLeft = true;
        while(aItr.hasNext() && bItr.hasNext()) {
            if (isLeft) {
                items.add(aItr.next());
            } else {
                items.add(bItr.next());
            }
            isLeft = !isLeft;
        }

        while(aItr.hasNext()) {
            items.add(aItr.next());
        }

        while(bItr.hasNext()) {
            items.add(bItr.next());
        }

        return items;
    }

    public static <T> List<T> merge(final Collection<T>... a) {
        final Iterator<T>[] itrs = new Iterator[a.length];
        for(int i=0; i<a.length; i++) {
            itrs[i] = (a==null) ? null : a[i].iterator();
        }

        final List<T> items = new ArrayList<>();
        boolean isDone = true;
        while(!isDone) {
            for(int i=0; i<a.length; i++) {
                if (itrs[i] != null && itrs[i].hasNext()) {
                    items.add(itrs[i].next());
                    isDone = false;
                }
            }
        }
        return items;
    }

    public static <T> List<T> riffleMerge(Collection<T>... collections) {
        final int[] threshold = new int[collections.length];
        final Iterator<T>[] itrs = new Iterator[collections.length];

        int sumSize = 0;
        for(int i=0; i<collections.length; i++) {
            sumSize += collections[i].size();
            threshold[i] = sumSize;
            itrs[i] = (collections[i]==null) ? null : collections[i].iterator();
        }

        List<T> items = new ArrayList<>();

        // Iterate over both lists randomly choosing one from each
        boolean done;
        do {
            final int choice = R.nextInt(sumSize);
            done = true;
            for(int i=0; i<threshold.length; i++) {
                if (threshold[i] > choice && itrs[i]!=null && itrs[i].hasNext()) {
                    done = false;
                    items.add(itrs[i].next());
                    break;
                }
            }
        } while(!done);

        items = cutShuffle(items);  // Cut once

        return items;
    }

    /**
     * Performs a true randomized shuffle at order n squared complexity
     * @param items
     * @param <T>
     * @return
     */
    public static <T> List<T> randomShuffle(final Collection<T> items) {
        final List<T> list = new ArrayList<>();
        for(T item : items) {
            final int i = R.nextInt(list.size()+1);
            list.add(i, item);
        }
        return list;
    }
}

