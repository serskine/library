package soupthatisthick.var;

import soupthatisthick.util.ifaces.comparison.Equatable;
import soupthatisthick.util.impl.readonly.ReadOnly;

import java.util.*;

public class Var implements Equatable {
    private Map<String, Var> properties = new TreeMap<>();
    private Object value = null;

    public static Var NULL_PROPERTY = new Var(null);

    public Var() {
        this(null);
    }

    public Var(final Object value) {
        set(value);
    }

    public boolean isAtomic() {
        return properties.isEmpty();
    }

    public Object get() {
        return value;
    }

    public Var set(final Object value) {
        this.properties.clear();
        if (value != null) {
            if (value instanceof Var) {
                final Var otherVar = (Var) value;
                this.value = otherVar.value;
                for(String propertyName : otherVar.getProperties()) {
                    set(propertyName, otherVar.get(propertyName));
                }
            } else {
                this.value = value;
            }
        } else {
            this.value = null;
        }
        return this;
    }

    public Var get(final String propertyName) {
        return properties.getOrDefault(propertyName, null);
    }

    public Var set(final String propertyName, final Object value) {
        if (value instanceof Var) {
            properties.put(propertyName, (Var) value);
        } else {
            return set(propertyName, new Var(value.toString()));
        }
        return this;
    }

    public Set<String> getProperties() {
        return ReadOnly.set(properties.keySet());
    }

    private final String describe(int indent, final String currentPath, final String name, final Map<Var, String> printed) {
        if (printed.keySet().contains(this)) {
            final String originalPath = printed.get(this);
            return originalPath;
        }
        final String pathName = currentPath + name;
        printed.put(this, pathName);

        final String tab = "  ";
        final StringBuilder sb = new StringBuilder();
        if (isAtomic()) {
            if (value==null) {
                sb.append("null");
            } else {
                sb.append("\"" + value + "\"");
            }
        } else {
            String indentation = "";
            for(int i=0; i<indent; i++) {
                indentation += tab;
            }
            final String delim = ",\n" + indentation + tab;

            boolean isFirst = true;
            sb.append(name);
            sb.append(": {\n");
            sb.append(indentation).append(tab);
            for (String propertyName : getProperties()) {
                if (!isFirst) {
                    sb.append(delim);
                }
                isFirst = false;
                final Var value = get(propertyName);
                sb.append(propertyName + ": " + value.describe(indent+1, pathName + "/", propertyName, printed));
            }
            sb.append("\n").append(indentation).append("}");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return describe(0, "", "root", new HashMap<>());
    }

    public String toString(final String name) {
        return describe(0, "", name, new HashMap<>());
    }

    @Override
    public boolean equals(Object other) {
        return isEqualTo(other);
    }

    @Override
    public boolean isEqualTo(Object other) {
        return this == other;
    }

    @Override
    public boolean isEquivalentTo(Object other) {
        if (isAtomic()) {
            if (other==null) {
                return value==null;
            } else if (other instanceof Var) {
                final Var otherVar = (Var) other;
                return (    otherVar.isAtomic()
                        &&  Objects.equals(value, otherVar.get())
                );
            } else {
                return Objects.equals(value, other);
            }
        } else {
            if (other==null) {
                return false;
            } else {
                if (other instanceof Var) {
                    final Var otherVar = (Var) other;
                    if (otherVar.isAtomic()) {
                        return false;
                    } else {
                        for(String propertyName : getProperties()) {
                            final Var pv1 = get(propertyName);
                            final Var pv2 = otherVar.get(propertyName);
                            final boolean isPropertySame = Objects.equals(pv1, pv2);
                            if (!isPropertySame) {
                                return false;
                            }
                        }
                        for(String propertyName : otherVar.getProperties()) {
                            final Var pv1 = get(propertyName);
                            final Var pv2 = otherVar.get(propertyName);
                            final boolean isPropertySame = Objects.equals(pv1, pv2);
                            if (!isPropertySame) {
                                return false;
                            }
                        }
                        return true;

                    }
                } else {
                    return false;
                }
            }
        }
    }
}
