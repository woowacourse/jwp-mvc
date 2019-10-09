package nextstep.mvc.tobe.resolver;


import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.exception.HandlerMethodArgumentResolverException;
import nextstep.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ObjectMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return methodParameter.isSameType(Object.class);
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        try {
            final HttpServletRequest request = webRequest.getRequest();
            final Class<?> parameterType = methodParameter.getType();
            final Field[] fields = parameterType.getDeclaredFields();

            final Constructor<?> constructor = parameterType.getDeclaredConstructor();
            constructor.setAccessible(true);
            final Object instance = constructor.newInstance();

            for (final Field field : fields) {
                final Object value = parseValue(field, request);
                field.setAccessible(true);
                field.set(instance, value);
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new HandlerMethodArgumentResolverException(e.getMessage());
        }
    }

    private Object parseValue(final Field field, final HttpServletRequest request) {
        final String value = request.getParameter(field.getName());
        return TypeConverter.to(field.getType()).apply(value);
    }
}
