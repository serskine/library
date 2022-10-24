package soupthatisthick.dynamic;

public class AtomicVariable<T> extends Variable {
    private T value;

    public AtomicVariable(Type type) {
        super(type);
    }

    @Override
    protected boolean isAtomicEquivelant(Variable other) {
        if (other == null) {
            return false;
        }
        if (other instanceof AtomicVariable<?>) {
            final AtomicVariable<?> otherAtomicVariable = (AtomicVariable<?>) other;
            return get().equals(otherAtomicVariable.get());
        }
        return false;
    }

    public T get() {
        return this.value;
    }

    public void set(final T value) {
        this.value = value;
    }

}
