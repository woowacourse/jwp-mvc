package nextstep.mvc.tobe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PrimitiveParser {
    private static Map<Class, Function<String, Object>> primitives = new HashMap<>();

    static {
        primitives.put(Boolean.class, Boolean::parseBoolean);
        primitives.put(boolean.class, Boolean::parseBoolean);
        primitives.put(Integer.class, Integer::parseInt);
        primitives.put(int.class, Integer::parseInt);
        primitives.put(Long.class, Long::parseLong);
        primitives.put(long.class, Long::parseLong);
        primitives.put(Float.class, Float::parseFloat);
        primitives.put(float.class, Float::parseFloat);
        primitives.put(Double.class, Double::parseDouble);
        primitives.put(double.class, Double::parseDouble);
        primitives.put(String.class, s -> s);
    }

    public static Object getPrimitive(Class<?> type, String value) {
        return primitives.get(type).apply(value);
    }
}
