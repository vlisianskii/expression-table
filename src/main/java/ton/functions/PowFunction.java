package ton.functions;

import lombok.AllArgsConstructor;
import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;

import java.util.stream.Stream;

@AllArgsConstructor
public class PowFunction implements IFunction {
    private final int exponent;

    @Override
    public Stream<Cell> dependencies(Table table, Column column, Row row) {
        return Stream.empty();
    }

    @Override
    public Double apply(Table table, Column column, Row row) {
        Cell cell = table.get(column, row);
        return Math.pow(cell.getResultRef().get(), exponent);
    }
}
