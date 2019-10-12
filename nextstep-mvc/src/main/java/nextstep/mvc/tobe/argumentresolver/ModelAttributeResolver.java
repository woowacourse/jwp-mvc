package nextstep.mvc.tobe.argumentresolver;

import com.google.common.collect.Lists;
import nextstep.web.annotation.ModelAttribute;
import nextstep.web.support.MethodParameter;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        Constructor[] constructors = type.getConstructors();

        for (Constructor constructor : constructors) {
            Class<?>[] constructorParamTypes = constructor.getParameterTypes();
            String[] constructorParamNames = NAME_DISCOVERER.getParameterNames(constructor);
            List<Object> constructorValues =
                    getArguments(request, constructorParamTypes, constructorParamNames);

            try {
                return constructor.newInstance(constructorValues.toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.getStackTrace();
            }
        }
        throw new IllegalArgumentException("fail to resolve object arguments!");
    }

    private List<Object> getArguments(HttpServletRequest request, Class<?>[] constructorParamTypes, String[] constructorParamNames) {
        List<Object> constructorValues = Lists.newArrayList();

        for (int j = 0; j < constructorParamNames.length; j++) {
            String constructorParamValue = request.getParameter(constructorParamNames[j]);
            if (Objects.nonNull(constructorParamValue)) {
                if (constructorParamTypes[j].isPrimitive()) {
                    constructorValues.add(WrapperClass.parsePrimitive(constructorParamTypes[j], constructorParamValue));
                    continue;
                }
                constructorValues.add(constructorParamValue);
            }
        }

        return constructorValues;
    }
}
