package nextstep.mvc;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

class ComponentScanner {

    private final Reflections reflections;

    private ComponentScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    static ComponentScanner of(Object... params) {
        return new ComponentScanner(new Reflections(params));
    }

    Set<Class<?>> getClassesAnnotated(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
