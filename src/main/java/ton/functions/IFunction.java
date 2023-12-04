package ton.functions;

import ton.Column;
import ton.Row;
import ton.Table;

public interface IFunction {
    Double apply(Table table, Column column, Row row);

    boolean isExecuted(Table table, Column column, Row row);

    default Double execute(Table table, Column column, Row row) {
        try {
            return apply(table, column, row);
        } catch (Exception ignore) {
            return null;
        }
    }
}
