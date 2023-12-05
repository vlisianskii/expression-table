package ton;

import lombok.Data;

@Data
public class Dependency {
    private final String variable;
    private final Row row;

    public Column getColumn(Column column) {
        if (variable.startsWith("prev_")) return column.prev();
        if (variable.startsWith("next_")) return column.next();
        return column;
    }
}
