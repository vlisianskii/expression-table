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

    public void putRow(Row row) {
        getColumns().forEach(column -> putCell(column, row, newArrayList(), null));
    }

    public void putCell(Column column, Row row, Double value) {
        putCell(column, row, newArrayList(), value);
    }

    public void putCell(Column column, Row row, IFunction function) {
        putCell(column, row, newArrayList(function));
    }

    public void putCell(Column column, Row row, List<IFunction> functions) {
        putCell(column, row, functions, null);
    }

    public void putCell(Column column, Row row, List<IFunction> functions, Double value) {
        super.put(column, row, new Cell(functions, value));
    }

    public Double getCell(Column column, Row row) {
        Cell cell = super.get(column, row);
        if (cell == null) {
            return null;
        }
        return cell.getResult(this, column, row);
    }
}
