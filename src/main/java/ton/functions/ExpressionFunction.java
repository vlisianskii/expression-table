package ton.functions;

import ton.*;
import ton.expression.Expression;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ExpressionFunction implements IFunction {
    private final List<Dependency> dependencies;
    private final Expression expression;

    public ExpressionFunction(String expression, List<Dependency> dependencies) {
        this.dependencies = dependencies;
        this.expression = new Expression(expression, dependencies.stream().map(Dependency::getVariable).collect(toSet()));
    }

    @Override
    public Double apply(Table table, Column column, Row row) {
        return expression.evaluate(variables(table, column, row));
    }

    @Override
    public Stream<Cell> dependencies(Table table, Column column, Row row) {
        return dependencies.stream()
                .map(dependency -> table.get(dependency.getColumn(column), dependency.getRow()));
    }

    private Map<String, Double> variables(Table table, Column column, Row row) {
        return dependencies.stream()
                .collect(toMap(Dependency::getVariable, dependency -> getDependentCell(table, column, row, dependency)));
    }

    private Double getDependentCell(Table table, Column column, Row row, Dependency dependency) {
        checkDependencies(row, dependency);
        Column resolvedColumn = dependency.getColumn(column);
        return table.get(resolvedColumn, dependency.getRow()).getResult(table, resolvedColumn, dependency.getRow());
    }

    private static void checkDependencies(Row row, Dependency dependency) {
        if (dependency.getRow().getName().equals(row.getName())) {
            throw new IllegalArgumentException(format("Recurring dependency <%s> for <%s> row", dependency, row));
        }
    }
}
