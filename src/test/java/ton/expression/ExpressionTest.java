package ton.expression;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class ExpressionTest {
    @Test
    public void test_simple_expression() {
        // arrange
        Expression expression = new Expression("0.2");
        // act
        Double result = expression.evaluate();
        // assert
        assertThat(result).isEqualTo(0.2);
    }

    @Test
    public void test_table_expression() {
        Expression expression = new Expression("(A + prev_A) / next_B", Set.of("A", "prev_A", "next_B"));

        // arrange
        Map<String, Double> variables = Map.of(
                "A", 1.1,
                "prev_A", 1.00004,
                "next_B", -1.2
        );
        // act
        Double result = expression.evaluate(variables);
        // assert
        assertThat(result).isEqualTo(-1.7500333333333333);

        // arrange updated variables and re-calculate
        Map<String, Double> newVariables = Map.of(
                "A", 2.3,
                "prev_A", 1.00004,
                "next_B", -1.2
        );
        // act updated variables
        Double newResult = expression.evaluate(newVariables);
        // assert updated variables
        assertThat(newResult).isEqualTo(-2.7500333333333336);

        // arrange updated variables and re-calculate
        Map<String, Double> notEnoughVariables = Map.of(
                "A", 2.3,
                "next_B", -1.2
        );
        // act updated variables
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> expression.evaluate(notEnoughVariables));
        // assert not enough variables
        assertThat(exception.getMessage()).isEqualTo("No value has been set for the setVariable 'prev_A'.");
    }
}
