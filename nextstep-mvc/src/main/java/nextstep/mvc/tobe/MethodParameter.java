package nextstep.mvc.tobe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Objects;

public class MethodParameter {
    private final Parameter parameter;
    private final String name;
    private final int index;

    public MethodParameter(final Parameter parameter, final String name, final int index) {
        this.parameter = parameter;
        this.name = name;
        this.index = index;
    }

    public boolean isAnnotationPresent(final Class<? extends Annotation> annotation) {
        return parameter.isAnnotationPresent(annotation);
    }

    public Parameter getParameter() {
        return parameter;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return parameter.getType();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MethodParameter that = (MethodParameter) o;
        return index == that.index &&
                Objects.equals(parameter, that.parameter) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, name, index);
    }
}
