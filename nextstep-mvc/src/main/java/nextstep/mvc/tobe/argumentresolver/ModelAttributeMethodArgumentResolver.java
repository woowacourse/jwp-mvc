package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.utils.ClassUtils;
import nextstep.web.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class ModelAttributeMethodArgumentResolver implements HandlerMethodArgumentResolver {
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
            e.printStackTrace();
        }
    }
}
