package ton;

import lombok.Data;
import ton.functions.IFunction;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class Cell<C extends IColumn, R extends IRow> {
    private final AtomicReference<Double> resultRef = new AtomicReference<>(null);
    private final AtomicReference<Boolean> executedRef = new AtomicReference<>(false);

    private final ExpressionTable<C, R> table;
    private final C column;
    private final R row;
    private final List<IFunction<C, R>> functions;

    public Cell(ExpressionTable<C, R> table, C column, R row, List<IFunction<C, R>> functions, Double initialValue) {
        this.table = table;
        this.column = column;
        this.row = row;
        this.functions = functions;
        this.resultRef.set(initialValue);
    }

    public Double getResult() {
        if (!isExecuted()) {
            functions.stream()
                    .map(func -> func.execute(table, column, row))
                    .forEach(resultRef::set);
            executedRef.set(true);
        }
        return resultRef.get();
    }

    public boolean isExecuted() {
        return executedRef.get() && functions.stream().allMatch(this::allDependenciesExecuted);
    }

    private boolean allDependenciesExecuted(IFunction<C, R> function) {
        return function.dependencies(table, column, row)
                .filter(Objects::nonNull)
                .allMatch(Cell::isExecuted);
    }
}
