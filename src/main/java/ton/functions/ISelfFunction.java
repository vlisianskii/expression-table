package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;

import java.util.stream.Stream;

public interface ISelfFunction extends IFunction {
    Double apply(Table table, Column column, Row row, Cell cell);

    @Override
    default Stream<Cell> dependencies(Table table, Column column, Row row) {
        return Stream.empty();
    }

    @Override
    default Double apply(Table table, Column column, Row row) {
        Cell cell = table.get(column, row);
        return apply(table, column, row, cell);
    }
}
