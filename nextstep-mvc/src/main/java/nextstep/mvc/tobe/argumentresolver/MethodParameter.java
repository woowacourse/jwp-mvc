package nextstep.mvc.tobe.argumentresolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Method method;
    private final Parameter parameter;
    private final String parameterName;

    public MethodParameter(Method method, Parameter parameter, String parameterName) {
        this.method = method;
        this.parameter = parameter;
        this.parameterName = parameterName;
    }

    public Class<?> getType() {
        return parameter.getType();
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        return parameter.isAnnotationPresent(annotation);
    }

    public boolean hasNoAnnotation() {
        return parameter.getDeclaredAnnotations().length == 0;
    }

    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotation) {
        return parameter.getDeclaredAnnotation(annotation);
    }

    public String getParameterName() {
        return parameterName;
    }
}
