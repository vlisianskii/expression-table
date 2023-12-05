package ton;

import ton.functions.IFunction;
import ton.table.TreeTable;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Comparator.comparing;

public class Table extends TreeTable<Column, Row, Cell> {
    public Table() {
        super(comparing(Column::getIndex), comparing(Row::getName));
    }

    public void putRow(Row row, List<IFunction> functions) {
        getColumns().forEach(column -> putCell(column, row, functions));
    }

    public void putCell(Column column, Row row, Double value) {
        putCell(column, row, newArrayList(), value);
    }

    public void putCell(Column column, Row row, List<IFunction> functions) {
        putCell(column, row, functions, null);
    }

    public void putCell(Column column, Row row, List<IFunction> functions, Double value) {
        super.put(column, row, new Cell(this, column, row, functions, value));
    }
}
