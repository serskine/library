package soupthatisthick.dynamic;

import soupthatisthick.desc.Describe;
import soupthatisthick.util.ifaces.comparison.Equatable;
import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.ifaces.relations.OneToOne;
import soupthatisthick.util.impl.relations.OneToManyImpl;
import soupthatisthick.util.impl.relations.OneToOneImpl;
import soupthatisthick.util.uniqueid.UniqueId;

import java.util.*;

public class Type implements Equatable {


    private static final OneToMany<Type, Type> INHERITANCE_TREE = new OneToManyImpl<>();
    private static final OneToOne<Class<?>, Type> CLASS_TYPES = new OneToOneImpl<>();

    public static final Type OBJECT = new Type();
    public static final Type BOOLEAN = new Type(Optional.of(OBJECT));
    public static final Type NUMBER = new Type(Optional.of(OBJECT));
    public static final Type INTEGER = new Type(Optional.of(NUMBER));
    public static final Type STRING = new Type(Optional.of(OBJECT));
    public static final Type PAIR = new Type(Optional.of(OBJECT),
            new String[]{"key", "value"}, new Type[]{STRING, OBJECT});
    public static final Type ENUM = new Type(Optional.of(OBJECT));


    public static Map<String, Type> buildProperties(final String[] names, final Type[] types) {
        final Map<String, Type> map = new HashMap<>();
        if (names == null || types == null || names.length != types.length) {
            throw new RuntimeException("To build a properties map, we need at least one property name and an equal number of matching types.");
        }
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], types[i]);
        }
        return map;
    }

    private final UniqueId id;
    private Map<String, Type> properties = new HashMap<>();

    static void validateCanInherit(Map<String, Type> childProperties, Map<String, Type> parentProperties) {
        final Set<String> invalidProperties = new HashSet<>();
        for(String name : childProperties.keySet()) {
            if (parentProperties.containsKey(name)) {
                invalidProperties.add(name);
            }
        }
        if (!invalidProperties.isEmpty()) {
            final String setDescription = Describe.container(invalidProperties);
            throw new RuntimeException(String.format("The following properties conflict with properties inherited from ancestors. %s", setDescription));
        }
    }

    public Type() {
        this(Optional.empty());
    }

    public Type(final Map<String, Type> propertiesMap) {
        this(Optional.empty(), propertiesMap);
    }

    public Type(final Optional<Type> parentTypeOpt) {
        this(parentTypeOpt, new HashMap<>());
    }

    public Type(final Optional<Type> parentTypeOpt, final Map<String, Type> propertiesMap) {
        this.id = new UniqueId();
        INHERITANCE_TREE.addSource(this);

        if (parentTypeOpt.isPresent()) {
            final Type parentType = parentTypeOpt.get();

            // This will throw an exception is we can't inherit the ancestors properties
            validateCanInherit(propertiesMap, parentType.getProperties());
            this.properties.putAll(propertiesMap);

            INHERITANCE_TREE.link(parentTypeOpt.get(), this);
        } else {
            this.properties.putAll(propertiesMap);
        }
    }

    public Type(final String[] names, final Type[] types) {
        this(Optional.empty(), names, types);
    }

    public Type(final Optional<Type> parentOpt, final String[] names, final Type[] types) {
        this(parentOpt, buildProperties(names, types));
    }


    public boolean isAtomic() {
        return getProperties().isEmpty();
    }

    public Optional<Type> getParentType() {
        return INHERITANCE_TREE.getSourceOf(this);
    }

    /**
     * This assumes that all subclasses see the properties of their parents.
     * @return
     */
    public Map<String, Type> getProperties() {
        final Map<String, Type> allProperties = new HashMap<>();
        allProperties.putAll(properties);
        final Optional<Type> parentTypeOpt = getParentType();
        if (parentTypeOpt.isPresent()) {
            allProperties.putAll(parentTypeOpt.get().getProperties());
        }
        return allProperties;
    }

    public boolean isAssignableTo(Type otherType) {
        if (this.equals(otherType)) {
            return true;
        } else {
            final Optional<Type> parentTypeOpt = INHERITANCE_TREE.getSourceOf(this);
            if (parentTypeOpt.isPresent()) {
                return parentTypeOpt.get().isAssignableTo(otherType);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean isEqualTo(Object other) {
        return (other != null) && (other instanceof Type) && id.isEqualTo(((Type) other).id);
    }

    /**
     * This will determine if two types are equivelant to each other. This occurs when each has
     * the same set of properties and the types of those properties are equivelant to each other recursively.
     * @param other
     * @return
     */
    @Override
    public boolean isEquivalentTo(Object other) {
        if ((other != null) && (other instanceof Type)) {
            final Type t = (Type) other;
            if (isAtomic()) {
                return id.isEqualTo(t.id);
            } else {
                final Map<String, Type> myProperties = this.getProperties();
                final Map<String, Type> otherProperties = t.getProperties();
                final boolean isSameSize = myProperties.size() == otherProperties.size();
                if (!isSameSize) {
                    return false;
                }
                for(String propertyName : this.properties.keySet()) {
                    final boolean otherContainsProperty = otherProperties.containsKey(propertyName);
                    if (!otherContainsProperty) {
                        return false;
                    } else {
                        final Type myPropertyType = myProperties.get(propertyName);
                        final Type otherPropertyType = otherProperties.get(propertyName);
                        final boolean propertyNameHasSameType = myPropertyType.isEquivalentTo(otherPropertyType);
                        if (!propertyNameHasSameType) {
                            return false;
                        }
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        Describe.map(properties);
        return sb.toString();
    }
}
