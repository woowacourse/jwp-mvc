package nextstep.mvc.tobe.resolver;


import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.exception.HandlerMethodArgumentResolverException;
import nextstep.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Optional;
import java.util.stream.Stream;

// todo primitive - javabean 나누기
public class DefaultHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        final HttpServletRequest request = webRequest.getRequest();
        final String value = request.getParameter(methodParameter.getName());
        final Parameter parameter = methodParameter.getParameter();

        if (TypeConverter.contains(parameter.getType())) {
            return TypeConverter.to(parameter.getType()).apply(value);
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
        return TypeConverter.to(field.getType()).apply(value);
    }
}
