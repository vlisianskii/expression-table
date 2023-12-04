package ton.functions;

import lombok.Data;
import ton.Column;
import ton.Row;

@Data
public class Variable {
    private final Column column;
    private final Row row;
}
