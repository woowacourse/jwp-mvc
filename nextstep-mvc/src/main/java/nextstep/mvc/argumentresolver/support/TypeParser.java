package nextstep.mvc.argumentresolver.support;

import nextstep.mvc.exception.NotSupportedPrimitiveTypeException;

import java.util.Arrays;
import java.util.function.Function;

public enum TypeParser {
    INT(int.class, Integer::parseInt, 0),
    LONG(long.class, Long::parseLong, 0L),
    BOOLEAN(boolean.class, Boolean::parseBoolean, false),
    BYTE(byte.class, Byte::parseByte, 0),
    SHORT(short.class, Short::parseShort, 0),
    DOUBLE(double.class, Double::parseDouble, 0.0d),
    FLOAT(float.class, Float::parseFloat, 0.0f),
    STRING(String.class, string -> string, null);

    private Class<?> valueType;
    private Function<String, Object> function;
    private Object defaultValue;

    TypeParser(Class<?> valueType, Function<String, Object> function, Object defaultValue) {
        this.valueType = valueType;
        this.function = function;
        this.defaultValue = defaultValue;
    }

    public static Object parse(String source, Class<?> valueType) {
        TypeParser parser = Arrays.stream(values())
                .filter(type -> type.valueType.equals(valueType))
                .findAny()
                .orElseThrow(NotSupportedPrimitiveTypeException::new);

        if (source == null) {
            return parser.defaultValue;
        }
        return parser.function.apply(source);
    }

    public static boolean canParse(Class<?> valueType) {
        return Arrays.stream(values())
                .anyMatch(type -> type.valueType.equals(valueType));
    }
}