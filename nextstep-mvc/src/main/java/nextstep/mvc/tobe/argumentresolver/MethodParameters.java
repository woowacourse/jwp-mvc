package nextstep.mvc.tobe.argumentresolver;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MethodParameters {
    private static ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    private final List<MethodParameter> methodParameters;

    public MethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }

    public static MethodParameters of(Method method) {
        Parameter[] parameters = method.getParameters();
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
        List<MethodParameter> methodParameters = new ArrayList<>();

        for (int i = 0; i < method.getParameterCount(); i++) {
            methodParameters.add(new MethodParameter(parameters[i], Objects.requireNonNull(parameterNames)[i]));
        }

        return new MethodParameters(methodParameters);
    }

    public MethodParameter get(int index) {
        return methodParameters.get(index);
    }

    public int getParameterLength() {
        return methodParameters.size();
    }
}
