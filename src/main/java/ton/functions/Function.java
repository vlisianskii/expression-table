package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class Function implements IFunction {
    protected abstract Stream<Cell> dependencies(Table table, Column column, Row row);
    
    @Override
    public boolean isExecuted(Table table, Column column, Row row) {
        return dependencies(table, column, row).filter(Objects::nonNull).allMatch(Cell::isExecuted);
    }
}
