package ton.functions;

import lombok.AllArgsConstructor;
import ton.Cell;
import ton.ExpressionTable;
import ton.IColumn;
import ton.IRow;

import java.util.stream.Stream;

@AllArgsConstructor
public class PowFunction<C extends IColumn, R extends IRow> implements IFunction<C, R> {
    private final int exponent;

    @Override
    public Stream<Cell<C, R>> dependencies(ExpressionTable<C, R> table, C column, R row) {
        return Stream.empty();
    }

    @Override
    public Double apply(ExpressionTable<C, R> table, C column, R row) {
        Cell<C, R> cell = table.get(column, row);
        return Math.pow(cell.getResultRef().get(), exponent);
    }
}
