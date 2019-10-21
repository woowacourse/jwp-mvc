package nextstep.mvc.tobe.argumentResolver;

import nextstep.mvc.MethodParameter;
import nextstep.utils.BeanUtils;
import nextstep.web.annotation.ModelAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ModelAttributeArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(ModelAttributeArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.isAnnotated(ModelAttribute.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        Object instance = BeanUtils.createInstance(methodParameter.getType());
        Field[] fields = methodParameter.getType().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> setInstance(field, instance, request));
        return instance;
    }

    private void setInstance(Field field, Object instance, HttpServletRequest request) {
        try {
            field.setAccessible(true);
            field.set(instance, request.getParameter(field.getName()));
        } catch (IllegalAccessException e) {
            logger.error("Argument Resolve Error: ", e);
            throw new SetFieldException();
        }
    }
}
