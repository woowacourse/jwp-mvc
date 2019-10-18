package nextstep.mvc.tobe.controllermapper.adepter;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

public class MethodParameter {
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    private Method method;
    private int parameterIndex;

    public MethodParameter(Method method, int parameterIndex) {
        this.method = method;
        this.parameterIndex = parameterIndex;
    }

    public Class<?> getParameterType() {
        return method.getParameterTypes()[parameterIndex];
    }

    public String getParameterName() {
        return NAME_DISCOVERER.getParameterNames(method)[parameterIndex];
    }

    public boolean checkType(Class<?> clazz) {
        return getParameterType().equals(clazz);
    }
}
