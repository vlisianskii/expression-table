package ton.functions;

import ton.Cell;
import ton.Column;
import ton.Row;
import ton.Table;
import ton.expression.Expression;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ExpressionFunction implements IFunction {
    private final Pattern ROW_PATTERN = Pattern.compile("(?<=_)(.*)");

    private final Expression expression;
    private final Set<String> variables;

    public ExpressionFunction(String expression, Set<String> variables) {
        this.expression = new Expression(expression, variables);
        this.variables = variables;
    }

    @Override
    public Double apply(Table table, Column column, Row row) {
        Map<String, Double> variables = variables(table, column);
        return expression.evaluate(variables);
    }

    @Override
    public Stream<Cell> dependencies(Table table, Column column, Row row) {
        return variables.stream()
                .map(dependency -> getDependentCell(table, column, dependency));
    }

    private Map<String, Double> variables(Table table, Column column) {
        return variables.stream()
                .map(dependency -> new AbstractMap.SimpleEntry<>(dependency, getDependentCell(table, column, dependency)))
                .filter(o -> o.getValue() != null)
                .collect(toMap(AbstractMap.SimpleEntry::getKey, v -> v.getValue().getResult()));
    }

    private Cell getDependentCell(Table table, Column column, String dependency) {
        Column dependentColumn = resolveColumn(column, dependency);
        Row dependentRow = resolveRow(dependency);
        return table.get(dependentColumn, dependentRow);
    }

    private Column resolveColumn(Column column, String dependency) {
        if (dependency.startsWith("prev_")) return column.prev();
        if (dependency.startsWith("next_")) return column.next();
        return column;
    }

    private Row resolveRow(String dependency) {
        Matcher matcher = ROW_PATTERN.matcher(dependency);
        if (matcher.find()) {
            return new Row(matcher.group(0));
        }
        return new Row(dependency);
    }
}
