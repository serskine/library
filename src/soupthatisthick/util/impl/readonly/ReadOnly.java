package soupthatisthick.util.impl.readonly;

import soupthatisthick.util.ifaces.relations.ManyToMany;
import soupthatisthick.util.ifaces.relations.OneToMany;
import soupthatisthick.util.ifaces.relations.OneToOne;
import soupthatisthick.util.impl.relations.ReadOnlyManyToMany;
import soupthatisthick.util.impl.relations.ReadOnlyOneToMany;
import soupthatisthick.util.impl.relations.ReadOnlyOneToOne;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ReadOnly {

    public static final <T> Set<T> set(final Set<T> theSet) {
        return Collections.unmodifiableSet(theSet);
    }

    public static final <K, V> Map<K, V> map(final Map<K, V> theMap) {
        return Collections.unmodifiableMap(theMap);
    }

    public static final <T> Collection<T> collection(final Collection<T> theCollection) {
        return Collections.unmodifiableCollection(theCollection);
    }

    public static final <K, V> OneToOne<K, V> oneToOne(final OneToOne<K, V> oneToOne) {
        return new ReadOnlyOneToOne<K, V>(oneToOne);
    }

    public static final <K, V> OneToMany<K, V> oneToMany(final OneToMany<K, V> oneToMany) {
        return new ReadOnlyOneToMany<K, V>(oneToMany);
    }

    public static final <K, V> ManyToMany<K, V> manyToMany(final ManyToMany<K, V> manyToMany) {
        return new ReadOnlyManyToMany<K, V>(manyToMany);
    }
}
