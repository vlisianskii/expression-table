package ton.table;

import java.util.Map;
import java.util.Set;

public interface ITable<C, R, V> {
    void put(C c, R r, V v);
    V get(C c, R r);
    Set<C> getColumns();
    Map<R, V> getRows(C c);
    boolean contains(C c, R r);
    boolean isEmpty();
    int size();
}
