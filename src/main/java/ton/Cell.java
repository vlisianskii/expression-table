package ton;

import lombok.Data;
import ton.functions.IFunction;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class Cell {
    private final AtomicReference<Double> resultRef = new AtomicReference<>(null);
    private final AtomicReference<Boolean> executedRef = new AtomicReference<>(false);

    private final List<IFunction> functions;

    public Cell(List<IFunction> functions, Double initialValue) {
        this.functions = functions;
        this.resultRef.set(initialValue);
    }

    public Double getResult(Table table, Column column, Row row) {
        if (!isExecuted(table, column, row)) {
            functions.stream()
                    .map(func -> func.execute(table, column, row))
                    .forEach(resultRef::set);
            executedRef.set(true);
        }
        return resultRef.get();
    }

    public boolean isExecuted(Table table, Column column, Row row) {
        return executedRef.get() && functions.stream().allMatch(func -> func.isExecuted(table, column, row));
    }
}
