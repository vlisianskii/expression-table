package ton.functions;

import lombok.AllArgsConstructor;
import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;

@AllArgsConstructor
public class PowFunction implements ISelfFunction {
    private final int exponent;

    @Override
    public Double apply(Table table, Column column, Row row, Cell cell) {
        return Math.pow(cell.getResultRef().get(), exponent);
    }
}
