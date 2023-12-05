package ton;

public interface IDependency<C extends IColumn, R extends IRow> {
    String getVariable();
    R getRow();
    C getColumn(C column);
}
