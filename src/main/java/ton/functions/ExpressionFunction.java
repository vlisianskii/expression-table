package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;
import ton.expression.Expression;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ExpressionFunction implements IFunction {
    private final Expression expression;

    public ExpressionFunction(String expression, Set<String> variables) {
        this.expression = new Expression(expression, variables);
    }

    @Override
    public Double apply(Table table, Column column, Row row) {
        Map<String, Double> variables = variables(table, column, row);
        return expression.evaluate(variables);
    }

    @Override
    public Stream<Cell> dependencies(Table table, Column column, Row row) {
        return row.getDependencies().stream()
                .map(dependency -> getDependentCell(table, column, dependency));
    }

    private Map<String, Double> variables(Table table, Column column, Row row) {
        return dependencies(table, column, row)
                .filter(Objects::nonNull)
                .collect(toMap(v -> v.getRow().getName(), Cell::getResult));
    }

    private Cell getDependentCell(Table table, Column column, Row dependency) {
        return table.get(resolveColumn(column, dependency), dependency);
    }

    private Column resolveColumn(Column column, Row dependency) {
        if (dependency.getName().startsWith("prev_")) column.prev();
        if (dependency.getName().startsWith("next_")) column.next();
        return column;
    }
}
