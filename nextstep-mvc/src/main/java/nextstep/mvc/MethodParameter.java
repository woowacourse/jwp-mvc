package nextstep.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Method method;
    private Parameter parameter;
    private Annotation[] annotations;

    public MethodParameter(Method method, Parameter parameter) {
        this.method = method;
        this.parameter = parameter;
    }

    private int validateIndex(Method method, int parameterIndex) {
        return 0;
    }

    public boolean isAnnotated(Class<? extends Annotation> clazz) {
        return method.isAnnotationPresent(clazz);
    }

    public Annotation getAnnotation(Class<? extends Annotation> clazz) {
        return parameter.getAnnotation(clazz);
    }

    public Class<?> getType() {
        return parameter.getType();
    }
}
