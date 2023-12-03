package ton;

import ton.table.TreeTable;

import static java.util.Comparator.comparing;

public class Table extends TreeTable<Column, Row, Cell> {
    public Table() {
        super(comparing(Column::getIndex), comparing(Row::getName));
    }

    public void putCell(Cell cell) {
        super.put(cell.getColumn(), cell.getRow(), cell);
    }
}
