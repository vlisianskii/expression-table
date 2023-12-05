package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;

import java.util.stream.Stream;

public interface IFunction {
    Stream<Cell> dependencies(Table table, Column column, Row row);
    Double apply(Table table, Column column, Row row);

    default Double execute(Table table, Column column, Row row) {
        try {
            return apply(table, column, row);
        } catch (Exception ignore) {
            return null;
        }
    }
}
