package nextstep.web.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Parameter parameter;
    private final String name;
    private final String path;

    public MethodParameter(Parameter parameter, String name, String path) {
        this.parameter = parameter;
        this.name = name;
        this.path = path;
    }

    public String getParameterName() {
        return this.name;
    }

    public Class<?> getParameterType() {
        return this.parameter.getType();
    }

    public Parameter getParameter() {
        return this.parameter;
    }

    public String getPath() {
        return this.path;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotation) {
        return this.parameter.isAnnotationPresent(annotation);
    }
}
