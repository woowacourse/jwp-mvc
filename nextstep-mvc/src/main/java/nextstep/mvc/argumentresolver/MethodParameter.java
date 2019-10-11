package nextstep.mvc.argumentresolver;

import nextstep.web.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Method method;
    private final Parameter parameter;
    private final String name;

    public MethodParameter(Method method, Parameter parameter, String name) {
        this.method = method;
        this.parameter = parameter;
        this.name = name;
    }

    public Class<?> getType() {
        return parameter.getType();
    }

    public boolean hasNoDeclaredAnnotation() {
        return parameter.getDeclaredAnnotations().length == 0;
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        return parameter.isAnnotationPresent(annotation);
    }

    public String getParameterName() {
        return name;
    }

    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotation) {
        return parameter.getDeclaredAnnotation(annotation);
    }
}
