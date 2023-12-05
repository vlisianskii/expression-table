package ton;

import lombok.Data;

import static java.lang.String.valueOf;

@Data
public class Column implements IColumn {
    private final Integer index;

    @Override
    public String getName() {
        return valueOf(index);
    }

    @Override
    public Column next() {
        return offset(1);
    }

    @Override
    public Column prev() {
        return offset(-1);
    }

    public Column offset(int offset) {
        return new Column(index + offset);
    }
}
