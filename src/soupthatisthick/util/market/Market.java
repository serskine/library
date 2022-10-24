package soupthatisthick.util.market;

import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.*;

public class Market<Key> implements Map<Key, Double> {

    private Map<Key, Double> map = new HashMap<>();
    private double sumWeight = 0D;

    private void validateValue(double value) {
        if (value<0D) {
            throw new RuntimeException("Value must be positive");
        }
    }

    private void validateProbability(double value) {
        if (value < 0D) {
            throw new RuntimeException("Probability can't be negative");
        }
        if (value > 1D) {
            throw new RuntimeException("Probability can't ne more than 100%");
        }
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Double get(Object key) {
        return this.map.getOrDefault(key, 0D);
    }

    public Double getWeight(Object key) {
        return get(key);
    }

    @Override
    public Double put(Key key, Double value) {
        validateValue(value);
        final Double old = get(key);
        sumWeight += (value.doubleValue() - old.doubleValue());
        map.put(key, value);
        return old;
    }

    @Override
    public Double remove(Object key) {
        double w = map.getOrDefault(key, 0D);
        final Double old = map.remove(key);
        sumWeight -= old.doubleValue();
        return old;
    }

    @Override
    public void putAll(Map<? extends Key, ? extends Double> m) {
        for(Key key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Key> keySet() {
        return ReadOnly.set(map.keySet());
    }

    @Override
    public Collection<Double> values() {
        return ReadOnly.collection(map.values());
    }

    @Override
    public Set<Entry<Key, Double>> entrySet() {
        return ReadOnly.set(map.entrySet());
    }

    public void normalize() {
        normalize(1D);
    }

    public void normalize(final double newSumWeight) {
        final Map<Key, Double> newMap = new HashMap<>();
        for(Key key : map.keySet()) {
            newMap.put(key, map.get(key).doubleValue() * newSumWeight / sumWeight);
        }
        map = newMap;
        sumWeight = newSumWeight;
    }

    public double getProb(Key key) {
        return (sumWeight > 0D) ? (get(key) / sumWeight) : 0D;
    }

    public double getSumWeight() {
        return this.sumWeight;
    }

    public Set<Key> getMax() {
        final Set<Key> keySet = new HashSet<>();
        Double max = null;
        for(Key key : keySet) {
            final Double v = get(key);
            if (max==null) {
                max = v;
            }
            if (v > max) {
                keySet.clear();
            }
            if (v >= max) {
                keySet.add(key);
            }
        }
        return keySet;
    }

    public Set<Key> getMin() {
        final Set<Key> keySet = new HashSet<>();
        Double min = null;
        for(Key key : keySet) {
            final Double v = get(key);
            if (min==null) {
                min = v;
            }
            if (v < min) {
                keySet.clear();
            }
            if (v <= min) {
                keySet.add(key);
            }
        }
        return keySet;
    }
}
