package ton;

import lombok.Data;

import java.util.List;

import static java.util.Collections.emptyList;

@Data
public class Row {
    private final String name;
//    private final List<Dependency> dependencies;

//    public Row(String name) {
//        this(name, emptyList());
//    }
//
//    public Row(String name, Dependency dependency) {
//        this(name, List.of(dependency));
//    }

    public Row(String name) {
//        checkDependencies(name, dependencies);
        this.name = name;
    }

//    private static void checkDependencies(String name, List<Dependency> dependencies) {
//        if (dependencies.stream().anyMatch(dependency -> dependency.getRow().getName().equals(name))) {
//            throw new IllegalArgumentException(format("Recurring dependencies <%s> for <%s> row", dependencies, name));
//        }
//    }
}
