package nextstep.mvc.tobe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodParameter {
    private final Parameter parameter;
    private final Method method;
    private final List<Annotation> annotations;

    public MethodParameter(Parameter parameter, Method method) {
        this.parameter = parameter;
        this.method = method;
        this.annotations = extractAnnotationTypes(parameter);
    }

    private List<Annotation> extractAnnotationTypes(Parameter parameter) {
        return Arrays.stream(parameter.getAnnotations())
                .collect(Collectors.toList());
    }

    public boolean isAnnotatedWith(Class<? extends Annotation> annotation) {
        return parameter.isAnnotationPresent(annotation);
    }

    public Class<?> getType() {
        return parameter.getType();
    }

    public Annotation getAnnotation(Class<? extends Annotation> annotation) {
        return parameter.getAnnotation(annotation);
    }
}
