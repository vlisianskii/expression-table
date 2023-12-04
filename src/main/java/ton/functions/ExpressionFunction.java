package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;
import ton.expression.Expression;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ExpressionFunction extends Function {
    private static final String VARIABLES_REGEX = "[^a-zA-Z0-9_\\s]";

    private final Set<String> variables;
    private final Expression expression;

    public ExpressionFunction(String expression) {
        this.variables = variables(expression);
        this.expression = new Expression(expression, variables);
    }

    @Override
    public Double apply(Table table, Column column, Row row) {
        return expression.evaluate(variables(table, column, row));
    }

    @Override
    public Stream<Cell> dependencies(Table table, Column column, Row row) {
        return variables.stream()
                .map(dependency -> getDependency(table, column, row, dependency))
                .filter(Objects::nonNull)
                .map(Map.Entry::getValue);
    }

    private Map<String, Double> variables(Table table, Column column, Row row) {
        return variables.stream()
                .map(dependency -> getDependency(table, column, row, dependency))
                .filter(Objects::nonNull)
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().getResult()));
    }

    private Map.Entry<String, Cell> getDependency(Table table, Column column, Row row, String dependency) {
        Variable variable = Variable.of(column, row, dependency);
        Cell cell = table.get(variable.getColumn(), variable.getRow());
        if (cell == null) return null;

        return new AbstractMap.SimpleEntry<>(variable.getVariable(), cell);
    }

    private Set<String> variables(String expression) {
        return stream(expression.split(VARIABLES_REGEX))
                .map(String::trim)
                .filter(o -> !o.isEmpty())
                .collect(toSet());
    }
}
