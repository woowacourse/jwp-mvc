package nextstep.utils;

import java.util.Map;

public class ValueTargets {
    private final Map<String, Class<?>> targets;

    private ValueTargets(Map<String, Class<?>> targets) {
        this.targets = targets;
    }

    public static ValueTargets from(Map<String, Class<?>> targets) {
        return new ValueTargets(targets);
    }

    public boolean exist(String name, Class<?> clazz) {
        return targets.containsKey(name) && targets.get(name).equals(clazz);
    }
}
