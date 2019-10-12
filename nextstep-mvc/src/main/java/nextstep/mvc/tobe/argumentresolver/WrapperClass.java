package nextstep.mvc.tobe.argumentresolver;

import java.util.Arrays;
import java.util.function.Function;

public enum WrapperClass {
    STRING(String.class, null, String::toString, null),
    INTEGER(Integer.class, int.class, Integer::valueOf, Integer::parseInt),
    LONG(Long.class, long.class, Long::valueOf, Long::parseLong),
    FLOAT(Float.class, float.class, Float::valueOf, Float::parseFloat),
    DOUBLE(Double.class, double.class, Double::valueOf, Double::parseDouble);

    private Class<?> wrapperType;
    private Class<?> primitiveType;
    private Function<String, Object> wrapperParser;
    private Function<String, Object> primitiveParser;

    WrapperClass(Class<?> wrapperType, Class<?> primitiveType, Function<String, Object> wrapperParser, Function<String, Object> primitiveParser) {
        this.wrapperType = wrapperType;
        this.primitiveType = primitiveType;
        this.wrapperParser = wrapperParser;
        this.primitiveParser = primitiveParser;
    }

    public static Object parseWrapper(Class<?> clazz, String value) {
        return Arrays.stream(values())
                .filter(values -> clazz.equals(values.wrapperType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .wrapperParser.apply(value);
    }

    public static Object parsePrimitive(Class<?> clazz, String value) {
        return Arrays.stream(values())
                .filter(values -> clazz.equals(values.primitiveType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .primitiveParser.apply(value);
    }
}
