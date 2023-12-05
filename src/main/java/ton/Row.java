package ton;

import lombok.Data;

@Data
public class Row {
    private final String name;

    public Row(String name) {
        this.name = name;
    }
}
