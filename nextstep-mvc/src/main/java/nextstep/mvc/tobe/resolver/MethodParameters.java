package nextstep.mvc.tobe.resolver;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MethodParameters {
    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private List<MethodParameter> methodParams = new ArrayList<>();

    public MethodParameters(final Method method) {
        final String[] names = nameDiscoverer.getParameterNames(method);
        final Parameter[] params = method.getParameters();

        for (int i = 0; i < params.length; i++) {
            methodParams.add(new MethodParameter(params[i], names[i], i, method));
        }
    }

    public List<MethodParameter> getMethodParams() {
        return new ArrayList<>(methodParams);
    }
}
