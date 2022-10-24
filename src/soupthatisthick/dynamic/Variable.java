package soupthatisthick.dynamic;

import soupthatisthick.desc.Describe;
import soupthatisthick.util.ifaces.comparison.Equatable;
import soupthatisthick.util.uniqueid.UniqueId;

import java.util.HashMap;
import java.util.Map;

public class Variable implements Equatable {

    private UniqueId id;
    private Map<String, Variable> propertiesMap = new HashMap<>();
    private final Type type;


    public Variable(final Type type) {
        this.id = new UniqueId();
        this.type = type;
    }

    protected boolean isAtomicEquivelant(Variable other) {
        return isEquivalentTo(other);
    }

    public final Type getType() {
        return this.type;
    }

    public final Variable get(final String propertyName) {
        if (getType().isAtomic()) {
            throw new RuntimeException(String.format("Variable of type %s is atomic and has no properties.", getType()));
        } else if (!getType().getProperties().containsKey(propertyName)) {
            throw new RuntimeException(String.format("Variable of type %s does not contain property %s.", getType(), propertyName));
        } else if (!propertiesMap.containsKey(propertyName)) {
            throw new RuntimeException(String.format("Property %s is not initialized", propertyName));
        } else {
            return propertiesMap.get(propertyName);
        }
    }

    public final void set(final String propertyName, Variable variable) {
        if (getType().isAtomic()) {
            throw new RuntimeException(String.format("Variable of type %s is atomic and has no properties.", getType()));
        } else if (!getType().getProperties().containsKey(propertyName)) {
            final String typePropertiesDesc = Describe.container(getType().getProperties().keySet());
            throw new RuntimeException(
                String.format("Variable of type [%s] does not contain property [%s]. Variable properties are [%s].",
                        getType(),
                        propertyName,
                        typePropertiesDesc)
            );
        } else {
            final Type expectedType = getType().getProperties().get(propertyName);
            final Type variableType = variable.getType();
            if (variableType.isAssignableTo(expectedType)) {
                propertiesMap.put(propertyName, variable);
            } else {
                final String message = String.format("Variable of type %s can't be assigned to property %s of type %s.",
                        variableType, propertyName, expectedType);
                throw new RuntimeException(message);
            }
        }
    }

    @Override
    public final String toString() {
        if (getType().isAtomic()) {
            return id.toString();
        } else {
            return Describe.map(propertiesMap);
        }
    }

    @Override
    public final boolean isEqualTo(Object other) {
        return this==other;
    }

    @Override
    public final boolean isEquivalentTo(Object other) {
        if (isEqualTo(other)) {
            return true;
        } else if (! (other instanceof Variable)) {
            return false;
        } else {
            final Variable v = (Variable) other;
            if (!v.getType().equals(getType())) {
                return false;
            } else if (getType().isAtomic()) {
                return isAtomicEquivelant(v);
            } else {
                for(String property : getType().getProperties().keySet()) {
                    final Variable myValue = get(property);
                    final Variable hisValue = get(property);
                    if (!myValue.isEquivalentTo(hisValue)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }


}
