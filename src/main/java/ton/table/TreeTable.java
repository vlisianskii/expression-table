package ton.table;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Collections.emptyMap;

public class TreeTable<C, R, V> implements ITable<C, R, V> {
    private final Map<C, Map<R, V>> content;
    private final Comparator<R> rowComparator;

    public TreeTable(Comparator<C> columnComparator, Comparator<R> rowComparator) {
        this.content = new TreeMap<>(columnComparator);
        this.rowComparator = rowComparator;
    }

    @Override
    public void put(C c, R r, V v) {
        content.computeIfAbsent(c, o -> new TreeMap<>(rowComparator)).put(r, v);
    }

    @Override
    public V get(C c, R r) {
        return getRows(c).get(r);
    }

    @Override
    public Set<C> getColumns() {
        return content.keySet();
    }

    @Override
    public Map<R, V> getRows(C c) {
        return content.getOrDefault(c, emptyMap());
    }

    @Override
    public boolean contains(C c, R r) {
        return getRows(c).containsKey(r);
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public int size() {
        return content.values().stream().mapToInt(Map::size).sum();
    }
}
