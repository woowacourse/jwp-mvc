package nextstep.mvc.tobe.resolver;


import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.exception.HandlerMethodArgumentResolverException;
import nextstep.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

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
            final Object instance = parameterType.newInstance();

            for (final Field field : fields) {
                final Object value = parseValue(field, request);
                field.setAccessible(true);
                field.set(instance, value);
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new HandlerMethodArgumentResolverException(e.getMessage());
        }
    }

    private Object parseValue(final Field field, final HttpServletRequest request) {
        final String value = request.getParameter(field.getName());
        return TypeConverter.to(field.getType()).apply(value);
    }
}
