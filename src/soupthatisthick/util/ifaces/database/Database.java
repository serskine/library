package soupthatisthick.util.ifaces.database;

import soupthatisthick.dynamic.Type;
import soupthatisthick.dynamic.Variable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface Database extends List<Variable> {

    Map<String, Type> getColumns();
    Type getRecordType();

    default List<Variable> query(final Predicate<Variable> record) {
        return stream().filter(record).toList();
    }

}
