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

    public void putCell(C column, R row, Double value) {
        putCell(column, row, newArrayList(), value);
    }

    public void putCell(C column, R row, List<IFunction<C, R>> functions) {
        putCell(column, row, functions, null);
    }

    public void putCell(C column, R row, List<IFunction<C, R>> functions, Double value) {
        super.put(column, row, new Cell<>(this, column, row, functions, value));
    }

    public void addFunctionsInRow(R row, List<IFunction<C, R>> functions) {
        getColumns().forEach(column -> addCellFunctions(column, row, functions, true));
    }

    public void addFunctionsInColumn(C column, List<IFunction<C, R>> functions) {
        getRows().forEach(row -> addCellFunctions(column, row, functions, false));
    }

    private void addCellFunctions(C column, R row, List<IFunction<C, R>> functions, boolean createNewCellIfNeeded) {
        Cell<C, R> cell = super.get(column, row);
        if (cell != null) {
            cell.getFunctions().addAll(functions);
            return;
        }
        if (createNewCellIfNeeded) {
            putCell(column, row, functions);
        }
    }

}
