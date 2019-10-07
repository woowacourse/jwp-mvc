package nextstep.mvc.tobe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerMethod {
    private final Object handler;
    private final Method handlerMethod;
    private final List<MethodParameter> methodParameters;

    public HandlerMethod(Object handler, Method handlerMethod) {
        this.handler = handler;
        this.handlerMethod = handlerMethod;
        methodParameters = wrapMethodParameters(handlerMethod);
    }

    private List<MethodParameter> wrapMethodParameters(Method handlerMethod) {
        return Arrays.stream(handlerMethod.getParameters())
                .map(parameter -> new MethodParameter(parameter, handlerMethod))
                .collect(Collectors.toList());
    }

    public Object invoke(Object... arguments) throws InvocationTargetException, IllegalAccessException {
        return handlerMethod.invoke(handler, arguments);
    }

    public List<MethodParameter> getMethodParameters() {
        return Collections.unmodifiableList(methodParameters);
    }
}
