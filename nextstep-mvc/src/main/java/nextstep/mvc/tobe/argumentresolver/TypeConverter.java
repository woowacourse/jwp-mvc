package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.argumentresolver.exception.NotSupportedPrimitiveTypeException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum TypeConverter {
    INT(int.class, Integer::parseInt, 0),
    LONG(long.class, Long::parseLong, 0L),
    BOOLEAN(boolean.class, Boolean::parseBoolean, false),
    BYTE(byte.class, Byte::parseByte, 0),
    SHORT(short.class, Short::parseShort, 0),
    DOUBLE(double.class, Double::parseDouble, 0.0d),
    FLOAT(float.class, Float::parseFloat, 0.0f),
    STRING(String.class, s -> s, null);

    private Class<?> valueType;
    private Function<String, Object> parser;
    private Object defaultValue;

    TypeConverter(Class<?> valueType, Function<String, Object> parser, Object defaultValue) {
        this.valueType = valueType;
        this.parser = parser;
        this.defaultValue = defaultValue;
    }

    public static boolean supports(Class<?> valueType) {
        return getPrimitiveParser(valueType).isPresent();
    }

    public static Object convert(String source, Class<?> valueType) {
        TypeConverter typeConverter = getPrimitiveParser(valueType)
                .orElseThrow(NotSupportedPrimitiveTypeException::new);

        if (source == null) {
            return typeConverter.defaultValue;
        }

        return typeConverter.parser.apply(source);
    }

    private static Optional<TypeConverter> getPrimitiveParser(Class<?> valueType) {
        return Arrays.stream(values())
                .filter(type -> type.valueType.equals(valueType))
                .findAny();
    }
}
