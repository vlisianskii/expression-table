package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;
import ton.expression.Expression;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ExpressionFunction implements IFunction {
    private final Expression expression;

    public ExpressionFunction(String expression, Row row) {
        this.expression = new Expression(expression, row.getDependencies().stream().map(Row::getName).collect(toSet()));
    }

    @Override
    public Stream<Cell> dependencies(Table table, Column column, Row row) {
        return row.getDependencies().stream()
                .map(dependency -> table.get(column, dependency));
    }

    @Override
    public Double apply(Table table, Column column, Row row) {
        Map<String, Double> variables = variables(table, column, row);
        return expression.evaluate(variables);
    }

    private Map<String, Double> variables(Table table, Column column, Row row) {
        return row.getDependencies().stream()
                .filter(Objects::nonNull)
                .collect(toMap(Row::getName, o -> getDependentCell(table, column, o).getResult()));
    }

    private Cell getDependentCell(Table table, Column column, Row dependency) {
        if (dependency.getName().startsWith("prev_")) {
            return table.get(column.prev(), dependency);
        }
        if (dependency.getName().startsWith("next_")) {
            return table.get(column.next(), dependency);
        }
        return table.get(column, dependency);
    }
}
