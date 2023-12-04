package ton;

import lombok.Data;

@Data
public class Column {
    private final Integer index;

    public Column next() {
        return offset(1);
    }

    public Column prev() {
        return offset(-1);
    }

    public Column offset(int offset) {
        return new Column(index + offset);
    }
}
