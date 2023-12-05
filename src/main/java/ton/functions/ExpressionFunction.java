package ton.functions;

import ton.Cell;
import ton.ExpressionTable;
import ton.IColumn;
import ton.IDependency;
import ton.IRow;
import ton.expression.Expression;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ExpressionFunction<C extends IColumn, R extends IRow> implements IFunction<C, R> {
    private final List<IDependency<C, R>> dependencies;
    private final Expression expression;

    public ExpressionFunction(String expression, List<IDependency<C, R>> dependencies) {
        this.dependencies = dependencies;
        this.expression = new Expression(expression, dependencies.stream().map(IDependency::getVariable).collect(toSet()));
    }

    @Override
    public Stream<Cell<C, R>> dependencies(ExpressionTable<C, R> table, C column, R row) {
        return dependencies.stream()
                .map(dependency -> table.get(dependency.getColumn(column), dependency.getRow()));
    }

    @Override
    public Double apply(ExpressionTable<C, R> table, C column, R row) {
        return expression.evaluate(variables(table, column, row));
    }

    private Map<String, Double> variables(ExpressionTable<C, R> table, C column, R row) {
        return dependencies.stream()
                .collect(toMap(IDependency::getVariable, dependency -> getDependentCell(table, column, row, dependency)));
    }

    private Double getDependentCell(ExpressionTable<C, R> table, C column, R row, IDependency<C, R> dependency) {
        checkDependencies(row, dependency);
        C resolvedColumn = dependency.getColumn(column);
        return table.get(resolvedColumn, dependency.getRow()).getResult();
    }

    private void checkDependencies(R row, IDependency<C, R> dependency) {
        if (dependency.getRow().getName().equals(row.getName())) {
            throw new IllegalArgumentException(format("Recurring dependency <%s> for <%s> row", dependency, row));
        }
    }
}
