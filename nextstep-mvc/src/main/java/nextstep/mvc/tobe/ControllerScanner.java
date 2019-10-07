package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerScanner {
    private Object[] basePackage;

    public ControllerScanner(Object[] basePackage) {
        this.basePackage = basePackage;

    }

    public Set<Class<?>> collectControllerReflections() {

        return Stream.of(basePackage)
                .map(Reflections::new)
                .flatMap(reflections -> reflections.getTypesAnnotatedWith(Controller.class).stream())
                .collect(Collectors.toSet());
    }
}
