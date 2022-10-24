package soupthatisthick.desc;

import java.util.Collection;
import java.util.Map;

public class Describe {
    public static final String container(final Collection<?> collection) {
        final StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean isFirst = true;
        for(final Object item : collection) {
            if (!isFirst) {
                sb.append(", ");
            }
            isFirst = false;
            sb.append(item.toString());
        }
        sb.append("}");
        return sb.toString();
    }

    public static final String map(final Map<?,?> map) {
        final StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean isFirst = true;
        for(final Map.Entry item : map.entrySet()) {
            if (!isFirst) {
                sb.append(", ");
            }
            isFirst = false;
            sb.append(item.toString());
        }
        sb.append("}");
        return sb.toString();
    }
}
