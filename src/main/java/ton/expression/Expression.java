package ton.expression;

import lombok.SneakyThrows;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

public class Expression {
    private final net.objecthunter.exp4j.Expression e;
    private final Field variablesField;

    public Expression(String expression) {
        this(expression, emptySet());
    }

    public Expression(String expression, Set<String> variables) {
        this.e = new ExpressionBuilder(expression).variables(variables).build();
        this.variablesField = getVariablesField(e);
    }

    public Double evaluate() {
        return evaluate(emptyMap());
    }

    public synchronized Double evaluate(Map<String, Double> variables) {
        clearVariables();
        return e.setVariables(variables).evaluate();
    }

    @SneakyThrows
    private void clearVariables() {
        boolean accessible = variablesField.trySetAccessible();
        variablesField.set(e, newHashMap());
        variablesField.setAccessible(accessible);
    }

    @SneakyThrows
    private Field getVariablesField(net.objecthunter.exp4j.Expression e) {
        return e.getClass().getDeclaredField("variables");
    }
}
