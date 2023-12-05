package ton.functions;

import ton.*;
import ton.expression.Expression;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ExpressionFunction extends Function {
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
                .collect(toMap(Dependency::getVariable, dep -> table.get(dep.getColumn(column), dep.getRow()).getResult()));
    }
}
