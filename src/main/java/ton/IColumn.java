package ton;

public interface IColumn {
    String getName();
    IColumn next();
    IColumn prev();
}
