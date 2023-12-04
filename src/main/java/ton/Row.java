package ton;

import lombok.Data;

import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.emptyList;

@Data
public class Row {
    private final String name;
    private final List<Row> dependencies;

    public Row(String name) {
        this(name, emptyList());
    }

    public Row(String name, Row dependency) {
        this(name, List.of(dependency));
    }

    public Row(String name, List<Row> dependencies) {
        checkDependencies(name, dependencies);
        this.name = name;
        this.dependencies = dependencies;
    }

    private static void checkDependencies(String name, List<Row> dependencies) {
        if (dependencies.stream().anyMatch(dependency -> dependency.getName().equals(name))) {
            throw new RuntimeException(format("Recurring dependencies <%s> for <%s> row", dependencies, name));
        }
    }
}
