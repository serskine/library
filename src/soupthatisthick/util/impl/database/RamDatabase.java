package soupthatisthick.util.impl.database;

import soupthatisthick.dynamic.Type;
import soupthatisthick.dynamic.Variable;
import soupthatisthick.util.ifaces.database.Database;

import java.util.*;

public class RamDatabase implements Database {

    private List<Variable> records = new ArrayList<>();
    private Type recordType;

    public RamDatabase(final Type recordType) {
        if (recordType.isAtomic()) {
            throw new RuntimeException(String.format("Database record type can't be atomic"));
        }
        final Map<String, Type> columnTypes = recordType.getProperties();
        for(String columnName : columnTypes.keySet()) {
            final Type value = columnTypes.get(columnName);
            if (!value.isAtomic()) {
                throw new RuntimeException(String.format("Column %s expects an atomic type", columnName));
            }
        }
        this.recordType = recordType;
    }

    @Override
    public Map<String, Type> getColumns() {
        return getRecordType().getProperties();
    }

    @Override
    public Type getRecordType() {
        return recordType;
    }

    public int size() {
        return records.size();
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean contains(Object o) {
        if (o==null) {
            return false;
        } else if (o instanceof Variable) {
            final Variable v = (Variable) o;
            if (!v.getType().isAssignableTo(getRecordType())) {
                return false;
            } else {
                return records.contains(v);
            }
        } else {
            return false;
        }
    }

    @Override
    public Iterator<Variable> iterator() {
        return records.iterator();
    }

    @Override
    public Object[] toArray() {
        return records.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return records.toArray(a);
    }

    @Override
    public boolean add(Variable variable) {
        validateRecord(variable);
        return records.add(variable);
    }

    @Override
    public boolean remove(Object o) {
        validateRecord(o);
        return records.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(item -> contains(item));
    }

    @Override
    public boolean addAll(Collection<? extends Variable> c) {
        boolean allAdded = true;
        for(Variable item : c) {
            allAdded = allAdded && this.add(item);
        }
        return allAdded;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Variable> c) {
        boolean allAdded = true;
        for(Variable item : c) {
            allAdded = allAdded && this.add(item);
        }
        return allAdded;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return records.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return records.retainAll(c);
    }

    @Override
    public void clear() {
        records.clear();;
    }

    @Override
    public Variable get(int index) {
        return records.get(index);
    }

    @Override
    public Variable set(int index, Variable element) {
        validateRecord(element);
        return records.set(index, element);
    }

    @Override
    public void add(int index, Variable element) {
        validateRecord(element);
        records.add(index, element);
    }

    @Override
    public Variable remove(int index) {
        return records.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        validateRecord(o);
        return records.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        validateRecord(o);
        return records.lastIndexOf(o);
    }

    @Override
    public ListIterator<Variable> listIterator() {
        return records.listIterator();
    }

    @Override
    public ListIterator<Variable> listIterator(int index) {
        return records.listIterator(index);
    }

    @Override
    public List<Variable> subList(int fromIndex, int toIndex) {
        return records.subList(fromIndex, toIndex);
    }

    public Variable getRow(int rowIndex) {
        return records.get(rowIndex);
    }

    private void validateRecord(final Object r) {
        if (r==null) {
            throw new RuntimeException("Database can not contain null records");
        } else if (!(r instanceof Variable)) {
            throw new RuntimeException("Database may only contain dynamic variables");
        } else {
            final Variable v = (Variable) r;
            if (!v.getType().isEquivalentTo(getRecordType())) {
                throw new RuntimeException(String.format("Record type %s is not equivelant to object type %s", getRecordType(), v.getType()));
            }
        }
    }
}
