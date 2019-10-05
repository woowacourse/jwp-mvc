package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentScan {
    private final Reflections reflections;

    public ComponentScan(final Reflections reflections) {
        this.reflections = reflections;
    }

    public Set<Method> getRequestMappingAnnotationPresentMethod() {
        return reflections.getTypesAnnotatedWith(Controller.class).stream()
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toSet());
    }
}
