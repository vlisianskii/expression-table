package ton.expression;

import lombok.SneakyThrows;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class Expression {
    private final net.objecthunter.exp4j.Expression e;
    private final Field variablesField;

    public Expression(String expression, Set<String> variables) {
        this.e = new ExpressionBuilder(expression).variables(variables).build();
        this.variablesField = getVariablesField(e);
    }

    public synchronized Double evaluate(Map<String, Double> variables) {
        clearVariables();
        return e.setVariables(variables).evaluate();
    }

    @SneakyThrows
    private void clearVariables() {
        variablesField.set(e, newHashMap());
    }

    @SneakyThrows
    private Field getVariablesField(net.objecthunter.exp4j.Expression e) {
        return e.getClass().getField("variables");
    }
}
