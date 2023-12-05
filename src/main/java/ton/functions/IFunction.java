package ton.functions;

import ton.Cell;
import ton.ExpressionTable;
import ton.IColumn;
import ton.IRow;

import java.util.stream.Stream;

public interface IFunction<C extends IColumn, R extends IRow> {
    Stream<Cell<C, R>> dependencies(ExpressionTable<C, R> table, C column, R row);
    Double apply(ExpressionTable<C, R> table, C column, R row);

    default Double execute(ExpressionTable<C, R> table, C column, R row) {
        try {
            return apply(table, column, row);
        } catch (Exception ignore) {
            return null;
        }
    }
}
