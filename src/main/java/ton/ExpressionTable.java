package ton;

import ton.functions.IFunction;
import ton.table.TreeTable;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Comparator.comparing;

public class ExpressionTable<C extends IColumn, R extends IRow> extends TreeTable<C, R, Cell<C, R>> {
    public ExpressionTable() {
        super(comparing(IColumn::getName), comparing(IRow::getName));
    }

    public void putRow(R row, List<IFunction<C, R>> functions) {
        getColumns().forEach(column -> putCell(column, row, functions));
    }

    public void putCell(C column, R row, Double value) {
        putCell(column, row, newArrayList(), value);
    }

    public void putCell(C column, R row, List<IFunction<C, R>> functions) {
        putCell(column, row, functions, null);
    }

    public void putCell(C column, R row, List<IFunction<C, R>> functions, Double value) {
        super.put(column, row, new Cell<>(this, column, row, functions, value));
    }
}
