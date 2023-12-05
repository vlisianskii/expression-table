package ton;

import org.junit.Test;
import ton.functions.ExpressionFunction;
import ton.functions.PowFunction;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TableTest {
    @Test
    public void test_expression_table() {
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

        ExpressionFunction b_function = new ExpressionFunction("A + prev_A", List.of(new Dependency("A", row_a), new Dependency("prev_A", row_a)));
        Cell cell_2022_b = new Cell(table, column_2022, row_b, b_function);
        Cell cell_2023_b = new Cell(table, column_2023, row_b, b_function);
        Cell cell_2024_b = new Cell(table, column_2024, row_b, b_function);

        ExpressionFunction c_function = new ExpressionFunction("B / next_B", List.of(new Dependency("B", row_b), new Dependency("next_B", row_b)));
        PowFunction c_2023_function = new PowFunction(3);
        Cell cell_2023_c = new Cell(table, column_2023, row_c, List.of(c_function, c_2023_function));

        PowFunction c_2024_function = new PowFunction(2);
        Cell cell_2024_c = new Cell(table, column_2024, row_c, List.of(c_2024_function), 3.0);

        // act
        table.putCell(cell_2022_a);
        table.putCell(cell_2023_a);
        table.putCell(cell_2024_a);

        table.putCell(cell_2022_b);
        table.putCell(cell_2023_b);
        table.putCell(cell_2024_b);

        table.putCell(cell_2023_c);
        table.putCell(cell_2024_c);

        // assert table
        assertThat(table.get(column_2022, row_c)).isNull();
        assertThat(table.get(column_2023, row_c).getResult()).isEqualTo(0.2160000000000001);
        assertThat(table.get(column_2024, row_c).getResult()).isEqualTo(9.0);

        assertThat(table.get(column_2022, row_b).getResult()).isNull();
        assertThat(table.get(column_2023, row_b).getResult()).isEqualTo(3.3000000000000003);
        assertThat(table.get(column_2024, row_b).getResult()).isEqualTo(5.5);

        assertThat(table.get(column_2022, row_a).getResult()).isEqualTo(1.1);
        assertThat(table.get(column_2023, row_a).getResult()).isEqualTo(2.2);
        assertThat(table.get(column_2024, row_a).getResult()).isEqualTo(3.3);

        // update table
        Cell replace_cell_2023_b = new Cell(table, column_2023, row_b, 3.5);
        table.putCell(replace_cell_2023_b);

        // assert updated table
        assertThat(table.get(column_2022, row_c)).isNull();
        assertThat(table.get(column_2023, row_c).getResult()).isEqualTo(0.25770097670924114);
        assertThat(table.get(column_2024, row_c).getResult()).isEqualTo(9.0);

        assertThat(table.get(column_2022, row_b).getResult()).isNull();
        assertThat(table.get(column_2023, row_b).getResult()).isEqualTo(3.5);
        assertThat(table.get(column_2024, row_b).getResult()).isEqualTo(5.5);

        assertThat(table.get(column_2022, row_a).getResult()).isEqualTo(1.1);
        assertThat(table.get(column_2023, row_a).getResult()).isEqualTo(2.2);
        assertThat(table.get(column_2024, row_a).getResult()).isEqualTo(3.3);
    }
}
