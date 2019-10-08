package nextstep.mvc.tobe.resolver;


import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.exception.HandlerMethodArgumentResolverException;
import nextstep.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Stream;

public class ObjectMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return methodParameter.isSameType(Object.class);
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        try {
            final HttpServletRequest request = webRequest.getRequest();
            final Field[] fields = methodParameter.getType().getDeclaredFields();
            final Optional<Constructor<?>> allArgsConstructor = getAllArgsConstructor(methodParameter, fields);

            if (allArgsConstructor.isPresent()) {
                return createByAllArgsConstructor(request, fields, allArgsConstructor.get());
            }
            return createByDefaultConstructor(request, fields, methodParameter);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HandlerMethodArgumentResolverException(e.getMessage());
        }
    }

    private Optional<Constructor<?>> getAllArgsConstructor(final MethodParameter methodParameter, final Field[] fields) {
        return Stream.of(methodParameter.getType().getConstructors())
                .filter(x -> x.getParameterCount() == fields.length)
                .findAny();
    }

    private Object createByAllArgsConstructor(final HttpServletRequest request, final Field[] fields, final Constructor<?> constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Object[] values = Stream.of(fields)
                .map(field -> parseValue(field, request))
                .toArray();
        return constructor.newInstance(values);
    }

    private Object createByDefaultConstructor(final HttpServletRequest request, final Field[] fields, final MethodParameter methodParameter) throws InstantiationException, IllegalAccessException {
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
        return TypeConverter.to(field.getType()).apply(value);
    }
}
