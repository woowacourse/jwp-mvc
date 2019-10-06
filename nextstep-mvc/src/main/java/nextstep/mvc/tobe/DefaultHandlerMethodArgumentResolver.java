package nextstep.mvc.tobe;


import nextstep.mvc.exception.HandlerMethodArgumentResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class DefaultHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private Map<Class<?>, Function<String, Object>> map = new HashMap<>();

    {
        map.put(int.class, Integer::parseInt);
        map.put(long.class, Long::parseLong);
        map.put(boolean.class, Boolean::parseBoolean);
        map.put(float.class, Float::parseFloat);
        map.put(double.class, Double::parseDouble);
        map.put(Long.class, Long::parseLong);
        map.put(Integer.class, Integer::parseInt);
        map.put(Boolean.class, Boolean::parseBoolean);
        map.put(Float.class, Float::parseFloat);
        map.put(Double.class, Double::parseDouble);
        map.put(String.class, x -> x);
    }

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter) {
        final String value = request.getParameter(methodParameter.getName());
        final Parameter parameter = methodParameter.getParameter();

        if (map.containsKey(parameter.getType())) {
            return map.get(parameter.getType()).apply(value);
        }

        return javaBean(request, methodParameter);
    }

    private Object javaBean(final HttpServletRequest request, final MethodParameter methodParameter) {
        try {
            final Field[] fields = methodParameter.getType().getDeclaredFields();

            final Optional<Constructor<?>> allArgsConstructor = Stream.of(methodParameter.getType().getConstructors())
                    .filter(x -> x.getParameterCount() == fields.length)
                    .findAny();

            return allArgsConstructor.isPresent() ?
                    createAllArgsConstructor(request, fields, allArgsConstructor.get())
                    : createDefaultConstructor(request, fields, methodParameter);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HandlerMethodArgumentResolverException(e.getMessage());
        }
    }

    private Object createAllArgsConstructor(final HttpServletRequest request, final Field[] fields, final Constructor<?> constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Object[] values = Stream.of(fields)
                .map(field -> parseValue(field, request))
                .toArray();
        return constructor.newInstance(values);
    }

    private Object createDefaultConstructor(final HttpServletRequest request, final Field[] fields, final MethodParameter methodParameter) throws InstantiationException, IllegalAccessException {
        final Object instance = methodParameter.getType().newInstance();
        for (final Field field : fields) {
            final Object value = parseValue(field, request);
            field.setAccessible(true);
            field.set(instance, value);
        }
        return instance;
    }

    private Object parseValue(final Field field, final HttpServletRequest request) {
        final String value = request.getParameter(field.getName());
        return map.get(field.getType()).apply(value);
    }
}
