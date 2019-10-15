package nextstep.mvc.tobe.argumentresolver;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public abstract class AbstractArgumentResolverTest {
    private ParameterNameDiscoverer nameDiscover = new LocalVariableTableParameterNameDiscoverer();

    Method getMethod(String name, Method[] methods) {
        return Arrays.stream(methods)
                .filter(method -> method.getName().equals(name))
                .findFirst()
                .get();
    }

    MethodParameter getNthMethodParam(Method method, int n) {
        Parameter parameter = method.getParameters()[n];
        String name = getParamNames(method)[n];
        return new MethodParameter(method, parameter, name);
    }

    private String[] getParamNames(Method method) {
        return nameDiscover.getParameterNames(method);
    }
}
