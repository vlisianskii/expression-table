package ton.functions;

import lombok.Data;
import ton.Column;
import ton.Row;

import static java.lang.String.format;

@Data
public class Variable {
    private static final String PREV_PREFIX = "prev_";
    private static final String NEXT_PREFIX = "next_";

    private final String variable;
    private final Column column;
    private final Row row;

    public static Variable of(Column column, Row row, String variable) {
        if (variable.startsWith(PREV_PREFIX)) return variable(
                variable, column.prev(), row, variable.replace(PREV_PREFIX, "")
        );
        if (variable.startsWith(NEXT_PREFIX)) return variable(
                variable, column.next(), row, variable.replace(NEXT_PREFIX, "")
        );
        return variable(variable, column, row, variable);
    }

    private static Variable variable(String variable, Column column, Row row, String dependency) {
        Row resolvedRow = resolveRow(row, dependency);
        return new Variable(variable, column, resolvedRow);
    }

    private static Row resolveRow(Row row, String dependency) {
        return row.getDependencies().stream()
                .filter(o -> o.getName().equals(dependency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("Cannot find <%s> dependency in <%s> row", dependency, row)));
    }
}
