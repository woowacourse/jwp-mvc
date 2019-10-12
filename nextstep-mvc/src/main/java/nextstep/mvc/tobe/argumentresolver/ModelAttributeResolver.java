package nextstep.mvc.tobe.argumentresolver;

import com.google.common.collect.Lists;
import nextstep.utils.ClassUtil;
import nextstep.web.annotation.ModelAttribute;
import nextstep.web.support.MethodParameter;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class ModelAttributeResolver implements HandlerMethodArgumentResolver {
    private static final ParameterNameDiscoverer NAME_DISCOVERER
            = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public boolean supports(MethodParameter parameter) {
        return parameter.hasAnnotation(ModelAttribute.class);
    }

    @Override
    public Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        Class<?> type = parameter.getParameterType();
        Constructor[] constructors = type.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            if (hasOnlyDefaultConstructor(constructors, constructor)) {
                return getArgumentWithDefaultConstructor(request, constructor, parameter.getParameterType());
            }

            if (constructor.getParameterCount() == 0) {
                continue;
            }

            List<Object> arguments =
                    getArgumentWithParamConstructor(request, constructor.getParameterTypes(),
                            NAME_DISCOVERER.getParameterNames(constructor));

            return ClassUtil.getNewInstance(constructor, arguments.toArray());
        }
        throw new IllegalArgumentException("fail to resolve object arguments!");
    }

    private boolean hasOnlyDefaultConstructor(Constructor[] constructors, Constructor constructor) {
        return constructors.length == 1 && constructor.getParameterCount() == 0;
    }

    private Object getArgumentWithDefaultConstructor(
            HttpServletRequest request, Constructor constructor, Class<?> clazz) {

        Object value = ClassUtil.getNewInstance(constructor);
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().isPrimitive()) {
                setField(field, value, WrapperClass.parsePrimitive(field.getType(), request.getParameter(field.getName())));
                continue;
            }
            setField(field, value, WrapperClass.parseWrapper(field.getType(), request.getParameter(field.getName())));
        }

        return value;
    }

    private void setField(Field field, Object instance, Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("error when setting fields with default constructor");
        }
    }

    private List<Object> getArgumentWithParamConstructor(
            HttpServletRequest request, Class<?>[] constructorParamTypes, String[] constructorParamNames) {

        List<Object> constructorValues = Lists.newArrayList();

        for (int j = 0; j < constructorParamNames.length; j++) {
            String constructorParamValue = request.getParameter(constructorParamNames[j]);
            if (Objects.nonNull(constructorParamValue)) {
                if (constructorParamTypes[j].isPrimitive()) {
                    constructorValues.add(WrapperClass.parsePrimitive(constructorParamTypes[j], constructorParamValue));
                    continue;
                }
                constructorValues.add(WrapperClass.parseWrapper(constructorParamTypes[j], constructorParamValue));
            }
        }

        return constructorValues;
    }
}
