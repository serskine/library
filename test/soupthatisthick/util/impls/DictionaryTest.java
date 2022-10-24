package soupthatisthick.util.impls;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soupthatisthick.util.impl.Dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    public static final String ERROR_EXPECTED_EXCEPTION = "Expected an exception to be thrown!";
    static final int DEFAULT_VALUE = 0;

    private Dictionary<String, Integer> dictionary;


    @BeforeEach
    public void onSetup() {
        dictionary = new Dictionary<>(DEFAULT_VALUE);
    }

    @AfterEach
    public void onTearDown() {
        dictionary = null;
    }

    @Test
    public void setup() {
        assertNotNull(dictionary);
    }

    @Test
    public void isEmpty() {
        assertTrue(dictionary.isEmpty());

        dictionary.put("A", DEFAULT_VALUE);

        assertTrue(dictionary.isEmpty());

        dictionary.put("A", DEFAULT_VALUE + 1);

        assertFalse(dictionary.isEmpty());

        dictionary.remove("A");

        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void putAndGet() {
        dictionary.put("A", 1);
        dictionary.put("B", 2);
        dictionary.put("C", DEFAULT_VALUE);

        assertEquals(1, dictionary.get("A"));
        assertEquals(2, dictionary.get("B"));
        assertEquals(DEFAULT_VALUE, dictionary.get("C"));
        assertEquals(DEFAULT_VALUE, dictionary.get("D"));
    }

    @Test
    public void keySet() {
        dictionary.put("A", 1);
        dictionary.put("B", 2);
        dictionary.put("C", DEFAULT_VALUE);

        Set<String> keys = dictionary.keySet();
        assertNotNull(keys);
        assertEquals(2, keys.size());
        assertTrue(keys.contains("A"));
        assertTrue(keys.contains("B"));
        assertFalse(keys.contains("C"));

        try {
            keys.add("X");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            if (ERROR_EXPECTED_EXCEPTION.equals(e.getMessage())) {
                fail(ERROR_EXPECTED_EXCEPTION);
            }
        }

        try {
            keys.remove("A");
            fail(ERROR_EXPECTED_EXCEPTION);
        } catch (Exception e) {
            if (ERROR_EXPECTED_EXCEPTION.equals(e.getMessage())) {
                fail(ERROR_EXPECTED_EXCEPTION);
            }
        }
    }

    @Test
    public void clear() {
        dictionary.put("A", 1);
        dictionary.put("B", 2);

        assertFalse(dictionary.isEmpty());

        dictionary.clear();

        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void containsValue() {
        assertFalse(dictionary.containsValue(DEFAULT_VALUE));
        dictionary.put("A", DEFAULT_VALUE);
        assertFalse(dictionary.containsValue(DEFAULT_VALUE));

        assertFalse(dictionary.containsValue(DEFAULT_VALUE + 1));
        dictionary.put("A", DEFAULT_VALUE + 1);
        assertTrue(dictionary.containsValue(DEFAULT_VALUE + 1));
    }


    @Test
    public void containsKey() {
        assertFalse(dictionary.containsKey("A"));
        dictionary.put("A", DEFAULT_VALUE);
        assertFalse(dictionary.containsKey("A"));

        assertFalse(dictionary.containsKey("B"));
        dictionary.put("B", DEFAULT_VALUE + 1);
        assertTrue(dictionary.containsKey("B"));
        dictionary.remove("B");
        assertFalse(dictionary.containsKey("B"));

    }

    @Test
    public void putAll() {
        final Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", DEFAULT_VALUE);

        dictionary.putAll(map);

        assertEquals(1, dictionary.get("A"));
        assertEquals(2, dictionary.get("B"));
        assertEquals(DEFAULT_VALUE, dictionary.get("C"));

        assertTrue(dictionary.containsKey("A"));
        assertTrue(dictionary.containsKey("B"));
        assertFalse(dictionary.containsKey("C"));

    }

    @Test
    public void values() {
        final Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", DEFAULT_VALUE);

        dictionary.putAll(map);

        final Collection<Integer> values = dictionary.values();
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));
        assertFalse(values.contains(DEFAULT_VALUE));
        assertEquals(2, values.size());
    }

    @Test
    public void entrySet() {
        final Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", DEFAULT_VALUE);

        dictionary.putAll(map);

        final Collection<Map.Entry<String, Integer>> entries = dictionary.entrySet();
        assertEquals(2, entries.size());
    }
}