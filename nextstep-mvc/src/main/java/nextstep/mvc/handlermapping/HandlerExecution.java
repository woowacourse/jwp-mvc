package nextstep.mvc.handlermapping;

import nextstep.mvc.argumentresolver.MethodParameter;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class HandlerExecution {
    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private Method method;
    private Object instance;

    public HandlerExecution(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public Object handle(Object... arguments) throws Exception {
        return method.invoke(instance, arguments);
    }

    public List<MethodParameter> extractMethodParameters() {
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();

        return IntStream.range(0, parameterNames.length)
                .mapToObj(i -> new MethodParameter(method, parameters[i], parameterNames[i]))
                .collect(Collectors.toUnmodifiableList());
    }
}