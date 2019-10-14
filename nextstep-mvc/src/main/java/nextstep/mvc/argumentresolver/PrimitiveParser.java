package nextstep.mvc.argumentresolver;

import nextstep.mvc.exception.NotSupportedPrimitiveTypeException;

import java.util.Arrays;
import java.util.function.Function;

public enum PrimitiveParser {
    INT(int.class, Integer::parseInt, 0),
    LONG(long.class, Long::parseLong, 0L),
    BOOLEAN(boolean.class, Boolean::parseBoolean, false),
    BYTE(byte.class, Byte::parseByte, 0),
    SHORT(short.class, Short::parseShort, 0),
    DOUBLE(double.class, Double::parseDouble, 0.0d),
    FLOAT(float.class, Float::parseFloat, 0.0f),
    STRING(String.class, s -> s, null);

    private Class<?> valueType;
    private Function<String, Object> function;
    private Object defaultValue;

    PrimitiveParser(Class<?> valueType, Function<String, Object> function, Object defaultValue) {
        this.valueType = valueType;
        this.function = function;
        this.defaultValue = defaultValue;
    }

    public static Object parse(String source, Class<?> valueType) {
        if (source == null) {
            return Arrays.stream(values())
                    .filter(type -> type.valueType.equals(valueType))
                    .map(type -> type.defaultValue)
                    .findAny()
                    .orElseThrow(NotSupportedPrimitiveTypeException::new);
        }
        return Arrays.stream(values())
                .filter(type -> type.valueType.equals(valueType))
                .map(type -> type.function.apply(source))
                .findAny()
                .orElseThrow(NotSupportedPrimitiveTypeException::new);
    }

    public static boolean canParse(Class<?> valueType) {
        return Arrays.stream(values())
                .anyMatch(type -> type.valueType.equals(valueType));
    }
}
