package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.utils.ClassUtils;
import nextstep.web.annotation.ModelAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class ModelAttributeMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(ModelAttributeMethodArgumentResolver.class);
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.isAnnotatedWith(ModelAttribute.class);
    }

    @Override
    public Object resolve(RequestContext requestContext, MethodParameter methodParameter) {
        Object argument = ClassUtils.newInstance(methodParameter.getType());
        Field[] fields = methodParameter.getType().getDeclaredFields();
        HttpServletRequest request = requestContext.getHttpServletRequest();

        for (Field field : fields) {
            setField(request, argument, field);
        }

        return argument;
    }

    private void setField(HttpServletRequest request, Object argument, Field field) {
        field.setAccessible(true);
        try {
            field.set(argument, request.getParameter(field.getName()));
        } catch (IllegalAccessException e) {
            logger.error("Exception : {}", e);
            throw new ObjectFieldSettingFailedException();
        }
    }
}
