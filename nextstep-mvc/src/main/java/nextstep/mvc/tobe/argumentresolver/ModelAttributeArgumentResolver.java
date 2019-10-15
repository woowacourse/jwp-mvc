package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.argumentresolver.exception.ArgumentResolveFailedException;
import nextstep.utils.ClassUtils;
import nextstep.utils.exception.ClassToInstanceFailedException;
import nextstep.web.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

public class ModelAttributeArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(ModelAttribute.class) ||
                (methodParameter.hasNoAnnotation() && !ServletArgument.supports(methodParameter.getType()));
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        try {
            Class<?> paramType = methodParameter.getType();
            Object instance = ClassUtils.classToInstance(paramType);

            for (Field field : paramType.getDeclaredFields()) {
                field.setAccessible(true);
                String parameter = request.getParameter(field.getName());

                field.set(instance, TypeConverter.convert(parameter, field.getType()));
            }

            return instance;
        } catch (IllegalAccessException | ClassToInstanceFailedException e) {
            throw new ArgumentResolveFailedException("request parameter 객체 매핑에 실패했습니다.", e);
        }
    }
}
