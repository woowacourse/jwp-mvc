package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerMethod {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMethod.class);

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

    public Object invoke(Object... arguments) {
        try {
            return handlerMethod.invoke(handler, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Http Request Exception : ", e);
            throw new HandlerMethodInvocationFailedException();
        }
    }

    public boolean isAnnotatedWith(Class<? extends Annotation> annotation) {
        return handlerMethod.isAnnotationPresent(annotation);
    }

    public List<MethodParameter> getMethodParameters() {
        return Collections.unmodifiableList(methodParameters);
    }
}
