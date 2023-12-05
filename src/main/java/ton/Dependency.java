package ton;

import lombok.Data;

@Data
public class Dependency<C extends IColumn, R extends IRow> implements IDependency<C, R> {
    private final String variable;
    private final R row;

    public C getColumn(C column) {
        if (variable.startsWith("prev_")) return (C) column.prev();
        if (variable.startsWith("next_")) return (C) column.next();
        return column;
    }
}
