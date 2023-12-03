package ton;

import lombok.Data;
import ton.functions.IFunction;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Collections.emptyList;

@Data
public class Cell {
    private final AtomicReference<Double> resultRef = new AtomicReference<>(null);
    private final AtomicReference<Boolean> executedRef = new AtomicReference<>(false);

    private final Table table;
    private final Column column;
    private final Row row;
    private final List<IFunction> functions;

    public Cell(Table table, Column column, Row row, Double result) {
        this(table, column, row, emptyList(), result);
    }

    public Cell(Table table, Column column, Row row, IFunction function) {
        this(table, column, row, List.of(function), null);
    }

    public Cell(Table table, Column column, Row row, List<IFunction> functions) {
        this(table, column, row, functions, null);
    }

    public Cell(Table table, Column column, Row row, List<IFunction> functions, Double result) {
        this.table = table;
        this.column = column;
        this.row = row;
        this.functions = functions;
        this.resultRef.set(result);
    }

    public Double getResult() {
        if (!isExecuted()) {
            functions.stream()
                    .map(func -> func.apply(table, column, row))
                    .forEach(resultRef::set);
            executedRef.set(true);
        }
        return resultRef.get();
    }

    public boolean isExecuted() {
        return executedRef.get() && functions.stream()
                .flatMap(func -> func.dependencies(table, column, row))
                .filter(Objects::nonNull)
                .allMatch(Cell::isExecuted);
    }
}
