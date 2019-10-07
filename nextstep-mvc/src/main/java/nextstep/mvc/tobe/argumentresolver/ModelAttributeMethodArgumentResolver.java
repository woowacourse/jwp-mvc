package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.utils.ClassUtils;
import nextstep.utils.InstanceCreationFailedException;
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
        try {
            Object argument = ClassUtils.newInstance(methodParameter.getType());
            Field[] fields = methodParameter.getType().getDeclaredFields();
            HttpServletRequest request = requestContext.getHttpServletRequest();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(argument, request.getParameter(field.getName()));
            }
            return argument;
        } catch (IllegalAccessException | InstanceCreationFailedException e) {
            logger.error("Http Request Exception : ", e);
            throw new ObjectFieldSettingFailedException();
        }
    }
}
