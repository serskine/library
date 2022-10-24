package soupthatisthick.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.ifaces.comparison.Equatable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeTest {




    @BeforeEach
    public void onSetup() {

    }

    @Test
    public void isAtomic() {

        final Type t1 = new Type();
        final Type t2 = new Type();

        final Map<String, Type> properties = buildProperties("p", t1, t1);

        final Type t3 = new Type(properties);
        final Type t4 = new Type(Optional.of(t3), new HashMap<>());

        assertTrue(t1.isAtomic());
        assertTrue(t2.isAtomic());
        assertFalse(t3.isAtomic());
        assertFalse(t4.isAtomic());
    }

    @Test
    public void isEqualTo() {

        final Type t1 = new Type();
        final Type t2 = new Type();

        final Map<String, Type> properties = buildProperties("p", t1, t1);

        final Type t3 = new Type(properties);
        final Type t4 = new Type(Optional.of(t3), new HashMap<>());

        assertTrue(t1.isEqualTo(t1));
        assertFalse(t1.isEqualTo(t2));
        assertFalse(t1.isEqualTo(t3));
        assertFalse(t1.isEqualTo(t4));

        assertFalse(t2.isEqualTo(t1));
        assertTrue(t2.isEqualTo(t2));
        assertFalse(t2.isEqualTo(t3));
        assertFalse(t2.isEqualTo(t4));

        assertFalse(t3.isEqualTo(t1));
        assertFalse(t3.isEqualTo(t2));
        assertTrue(t3.isEqualTo(t3));
        assertFalse(t3.isEqualTo(t4));

        assertFalse(t4.isEqualTo(t1));
        assertFalse(t4.isEqualTo(t2));
        assertFalse(t4.isEqualTo(t3));
        assertTrue(t4.isEqualTo(t4));
    }

    @Test
    public void isEquivelantTo_whenAtomic() {

        final Type t1 = new Type();
        final Type t2 = new Type(Optional.of(t1));
        final Type t3 = new Type();

        assertTrue(t1.isEquivalentTo(t1));
        assertFalse(t1.isEquivalentTo(t2));
        assertFalse(t2.isEquivalentTo(t1));
        assertTrue(t2.isEquivalentTo(t2));
    }

    @Test
    public void isEquivelantTo_whenNotAtomic() {

        final Type cType1 = new Type();
        final Type cType2 = new Type();

        final Map<String, Type> properties11 = buildProperties("p", cType1, cType1);
        final Map<String, Type> properties12 = buildProperties("p", cType1, cType2);
        final Map<String, Type> properties21 = buildProperties("p", cType2, cType1);
        final Map<String, Type> properties22 = buildProperties("p", cType2, cType2);

        final Type ta11 = new Type(properties11);
        final Type ta12 = new Type(properties12);
        final Type ta21 = new Type(properties21);
        final Type ta22 = new Type(properties22);

        final Type tb11 = new Type(properties11);
        final Type tb12 = new Type(properties12);
        final Type tb21 = new Type(properties21);
        final Type tb22 = new Type(properties22);

        // [1,1]
        assertEquivelant(ta11, ta11);
        assertNotEquivelant(ta11, ta12);
        assertNotEquivelant(ta11, ta21);
        assertNotEquivelant(ta11, ta22);

        assertEquivelant(ta11, tb11);
        assertNotEquivelant(ta11, tb12);
        assertNotEquivelant(ta11, tb21);
        assertNotEquivelant(ta11, tb22);

    }

    @Test
    public void isEquivelantTo_whenSubClassed_notAtomic() {
        final Type cType1 = new Type();
        final Type cType2 = new Type();

        final Map<String, Type> propertiesA = buildProperties("p[a]", cType1, cType2);
        final Map<String, Type> propertiesB = buildProperties("p[b]", cType1, cType2);

        final Type t1 = new Type(propertiesA);
        final Type t11 = new Type(Optional.of(t1));
        final Type t12 = new Type(Optional.of(t1), propertiesB);

        assertEquivelant(t1, t11);
        assertNotEquivelant(t1, t12);
        assertNotEquivelant(t11, t12);

    }

    @Test
    public void commonTypes() {
        Type textType = Type.STRING;
        Type booleanType = Type.BOOLEAN;
        Type numberType = Type.NUMBER;
        Type integerType = Type.INTEGER;

        assertNotEquivelant(textType, textType);
        assertNotEquivelant(textType, booleanType);
        assertNotEquivelant(textType, numberType);
        assertNotEquivelant(textType, integerType);

        assertNotEquivelant(booleanType, booleanType);
        assertNotEquivelant(booleanType, numberType);
        assertNotEquivelant(booleanType, integerType);

        assertNotEquivelant(numberType, numberType);
        assertNotEquivelant(numberType, integerType);

        assertNotEquivelant(integerType, integerType);

    }


    // --------------- Helper Methods --------------- //

    private void assertEquivelant(Equatable a, Equatable b) {
        final boolean isAvsB = a.isEquivalentTo(b);
        final boolean isBvsA = b.isEquivalentTo(a);

        assertTrue(isAvsB);
        assertTrue(isBvsA);
    }

    private void assertNotEquivelant(Equatable a, Equatable b) {
        final boolean isAvsB = a.isEquivalentTo(b);
        final boolean isBvsA = b.isEquivalentTo(a);

        assertFalse(isAvsB);
        assertFalse(isBvsA);
    }

    private Map<String, Type> buildProperties(final String prefix, final Type... types) {
        final Map<String, Type> map = new HashMap<>();
        for(int i=0; i<types.length; i++) {
            final Type propertyType = types[i];
            final String propertyName = prefix + "[" + i + "]";
            map.put(propertyName, propertyType);
        }
        return map;
    }
}
