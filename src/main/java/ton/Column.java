package ton;

import lombok.Data;

@Data
public class Column {
    private final Integer index;

    public Column next() {
        return new Column(index + 1);
    }

    public Column prev() {
        return new Column(index - 1);
    }
}
