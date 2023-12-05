package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;

import java.util.Objects;
import java.util.stream.Stream;

public interface IFunction {
    Double apply(Table table, Column column, Row row);

    Stream<Cell> dependencies(Table table, Column column, Row row);

    default Double execute(Table table, Column column, Row row) {
        try {
            return apply(table, column, row);
        } catch (Exception ignore) {
            return null;
        }
    }

    default boolean isExecuted(Table table, Column column, Row row) {
        return dependencies(table, column, row)
                .filter(Objects::nonNull)
                .allMatch(o -> o.isExecuted(table, column, row));
    }
}
