package nextstep.mvc.tobe.argumentresolver;


import nextstep.utils.PrimitiveParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ObjectArgumentResolver implements ArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(ObjectArgumentResolver.class);

    //TODO: Annotation 기반으로 or 다른 방식으로 어떻게 할 수 있을까
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return (!HttpServletRequest.class.equals(methodParameter.getParameterType()) &&
                !HttpServletResponse.class.equals(methodParameter.getParameterType())) &&
                !methodParameter.getParameterType().isPrimitive() &&
                !methodParameter.getParameterType().equals(String.class) &&
                Object.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        Class<?> type = methodParameter.getParameterType();
        Object parameterInstance = getInstance(type);

        for (Field field : type.getDeclaredFields()) {
            String param = request.getParameter(field.getName());
            setField(parameterInstance, field, PrimitiveParser.getPrimitive(field.getType(), param));
        }
        return parameterInstance;
    }

    private Object getInstance(Class<?> type) {
        try {
            Constructor constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            logger.error("Cannot create Instance: {}", type.toGenericString());
        }
        return null;
    }

    private void setField(Object instance, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            logger.error("Cannot set instance field - instance: {}, field - {}", instance.toString(), field.toGenericString());
        }
    }
}
