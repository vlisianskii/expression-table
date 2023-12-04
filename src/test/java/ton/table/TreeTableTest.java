package ton.table;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TreeTableTest {
    @Test
    public void test_empty_tree_table() {
        // arrange
        TreeTable<Integer, String, Double> table = new TreeTable<>(Integer::compareTo, String::compareTo);
        // assert
        assertThat(table.get(2023, "Sales")).isNull();
        assertThat(table.getRows(2023)).isEmpty();
        assertThat(table.contains(2023, "Sales")).isFalse();
        assertThat(table.isEmpty()).isTrue();
        assertThat(table.size()).isEqualTo(0);
    }

    @Test
    public void test_tree_table() {
        // arrange
        TreeTable<Integer, String, Double> table = new TreeTable<>(Integer::compareTo, String::compareTo);
        // act
        table.put(2023, "Sales", 100.01);
        // assert
        assertThat(table.get(2023, "Sales")).isEqualTo(100.01);
        assertThat(table.getRows(2023)).isEqualTo(Map.of("Sales", 100.01));
        assertThat(table.contains(2023, "Sales")).isTrue();
        assertThat(table.isEmpty()).isFalse();
        assertThat(table.size()).isEqualTo(1);

        // replace value
        table.put(2023, "Sales", 200.01);
        // assert replace value
        assertThat(table.get(2023, "Sales")).isEqualTo(200.01);
        assertThat(table.getRows(2023)).isEqualTo(Map.of("Sales", 200.01));
        assertThat(table.contains(2023, "Sales")).isTrue();
        assertThat(table.isEmpty()).isFalse();
        assertThat(table.size()).isEqualTo(1);
    }
}
