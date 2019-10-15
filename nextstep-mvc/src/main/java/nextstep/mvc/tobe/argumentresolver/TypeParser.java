package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.exception.NotMatchParserTypeException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public enum TypeParser {
    STRING(Collections.singletonList(String.class), String::toString),
    INTEGER(Arrays.asList(Integer.class, int.class), Integer::parseInt),
    LONG(Arrays.asList(Long.class, long.class), Long::parseLong),
    FLOAT(Arrays.asList(Float.class, float.class), Float::parseFloat),
    DOUBLE(Arrays.asList(Double.class, double.class), Double::parseDouble);

    private final List<Class<?>> types;
    private final Function<String, Object> parser;

    TypeParser(List<Class<?>> types, Function<String, Object> parser) {
        this.types = types;
        this.parser = parser;
    }

    public static Object parse(Class<?> clazz, String value) {
        return Arrays.stream(values())
                .filter(values -> values.types.contains(clazz))
                .findAny()
                .orElseThrow(NotMatchParserTypeException::new)
                .parser.apply(value);
    }
}
