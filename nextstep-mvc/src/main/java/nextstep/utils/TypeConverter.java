package nextstep.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum TypeConverter {
    SHORT(Arrays.asList(Short.class, short.class), Short::parseShort),
    INTEGER(Arrays.asList(Integer.class, int.class), Integer::parseInt),
    LONG(Arrays.asList(Long.class, long.class), Long::parseLong),
    BOOLEAN(Arrays.asList(Boolean.class, boolean.class), Boolean::parseBoolean),
    FLOAT(Arrays.asList(Float.class, float.class), Float::parseFloat),
    DOUBLE(Arrays.asList(Double.class, double.class), Double::parseDouble),
    STRING(Arrays.asList(String.class), x -> x);

    public static Map<Class<?>, Function<String, Object>> map = new HashMap<>();

    static {
        for (final TypeConverter typeConverter : TypeConverter.values()) {
            for (final Class<?> clazz : typeConverter.type) {
                map.put(clazz, typeConverter.converter);
            }
        }
    }

    private final List<Class<?>> type;
    private final Function<String, Object> converter;

    TypeConverter(final List<Class<?>> type, final Function<String, Object> converter) {
        this.type = type;
        this.converter = converter;
    }

    public static Function<String, Object> to(final Class type) {
        return map.get(type);
    }

    public static boolean contains(final Class type) {
        return map.containsKey(type);
    }
}
