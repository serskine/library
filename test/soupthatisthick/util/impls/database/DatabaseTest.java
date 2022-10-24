package soupthatisthick.util.impls.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.dynamic.Type;
import soupthatisthick.dynamic.Variable;
import soupthatisthick.util.ifaces.database.Database;
import soupthatisthick.util.impl.database.RamDatabase;
import soupthatisthick.util.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class DatabaseTest {

    private Type atomicType = new Type();
    private Variable v1, v2, v3, v4;

    private Database dbase;
    private Type recordType;
    private Map<String, Type> typeProperties;

    @BeforeEach
    public void onSetup() {
        v1 = new Variable(atomicType);
        v2 = new Variable(atomicType);
        v3 = new Variable(atomicType);
        v4 = new Variable(atomicType);

        typeProperties = new HashMap<>();

        typeProperties.put("propertyA", new Type());
        typeProperties.put("propertyB", new Type());
        typeProperties.put("propertyC", new Type());
        typeProperties.put("propertyD", new Type());

        this.recordType = new Type(typeProperties);

        Logger.info("recordType => " + this.recordType.toString());

        this.dbase = new RamDatabase(this.recordType);
    }

    @AfterEach
    public void onTearDown() {
        Logger.info("***************************");
        Logger.info("\n***\n*** Database contents " + dbase.size() + " x items\n***");
        for(Variable v : this.dbase.stream().toList()) {
            Logger.info(" - " + v.toString());
        }
        Logger.info("***************************");
    }

    @Test
    public void testSetup() {

    }

    @Test
    public void testAddingVariable() {
        final Variable r1 = buildRecord(v1, v2, v3, v4);
        final Variable r2 = buildRecord(v1, v2, v3, v4);

        dbase.add(r1);
        dbase.add(r2);
    }

    static Variable buildRecord(final Variable... variables) {
        final Map<String, Type> propertiesMap = new HashMap<>();
        final String names[] = new String[variables.length];

        for(int i=0; i<variables.length; i++) {
            names[i] = "[" + i + "]";
            propertiesMap.put(names[i], variables[i].getType());
        }

        final Type newType = new Type(propertiesMap);
        final Variable v = new Variable(newType);
        for(int i=0; i<names.length; i++) {
            v.set(names[i], variables[i]);
        }

        return v;
    }

}
