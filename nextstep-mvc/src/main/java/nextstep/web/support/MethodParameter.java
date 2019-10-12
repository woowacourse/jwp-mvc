package nextstep.web.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Parameter parameter;
    private final String name;

    public MethodParameter(Parameter parameter, String name) {
        this.parameter = parameter;
        this.name = name;
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

    public boolean hasAnnotation(Class<? extends Annotation> annotation) {
        return this.parameter.isAnnotationPresent(annotation);
    }
}
