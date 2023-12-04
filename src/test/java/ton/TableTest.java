package ton;

import org.junit.Test;
import ton.functions.ExpressionFunction;
import ton.functions.PowFunction;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TableTest {
    @Test
    public void test() {
        // arrange
        Table table = new Table();

        Column column_2022 = new Column(2022);
        Column column_2023 = new Column(2023);
        Column column_2024 = new Column(2024);

        Row row_a = new Row("A");
        Row row_b = new Row("B");
        Row row_c = new Row("C");

        Cell cell_2022_a = new Cell(table, column_2022, row_a, 1.1);
        Cell cell_2023_a = new Cell(table, column_2023, row_a, 2.2);
        Cell cell_2024_a = new Cell(table, column_2024, row_a, 3.3);

        ExpressionFunction b_function = new ExpressionFunction("A + prev_A", Set.of("A", "prev_A"));
        Cell cell_2022_b = new Cell(table, column_2022, row_b, b_function);
        Cell cell_2023_b = new Cell(table, column_2023, row_b, b_function);
        Cell cell_2024_b = new Cell(table, column_2024, row_b, b_function);

        ExpressionFunction c_function = new ExpressionFunction("B / next_B", Set.of("B", "next_B"));
        PowFunction powFunction = new PowFunction(2);
        Cell cell_2023_c = new Cell(table, column_2023, row_c, List.of(c_function, powFunction));

        // act
        table.putCell(cell_2022_a);
        table.putCell(cell_2023_a);
        table.putCell(cell_2024_a);

        table.putCell(cell_2022_b);
        table.putCell(cell_2023_b);
        table.putCell(cell_2024_b);

        table.putCell(cell_2023_c);

        // assert
        assertThat(table.get(column_2022, row_c)).isNull();
        assertThat(table.get(column_2023, row_c).getResult()).isEqualTo(0.3600000000000001);
        assertThat(table.get(column_2024, row_c)).isNull();

        assertThat(table.get(column_2022, row_b).getResult()).isNull();
        assertThat(table.get(column_2023, row_b).getResult()).isEqualTo(3.3000000000000003);
        assertThat(table.get(column_2024, row_b).getResult()).isEqualTo(5.5);

        assertThat(table.get(column_2022, row_a).getResult()).isEqualTo(1.1);
        assertThat(table.get(column_2023, row_a).getResult()).isEqualTo(2.2);
        assertThat(table.get(column_2024, row_a).getResult()).isEqualTo(3.3);
    }
}
