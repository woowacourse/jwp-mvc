package nextstep.mvc.tobe.argumentresolver;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MethodParameters {
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    private final List<MethodParameter> methodParameters;

    public MethodParameters(Method method) {
        Parameter[] params = method.getParameters();
        String[] names = NAME_DISCOVERER.getParameterNames(method);

        methodParameters = IntStream.range(0, params.length)
                .mapToObj(i -> new MethodParameter(method, params[i], names[i]))
                .collect(Collectors.toUnmodifiableList());
    }

    public Stream<MethodParameter> stream() {
        return methodParameters.stream();
    }
}
