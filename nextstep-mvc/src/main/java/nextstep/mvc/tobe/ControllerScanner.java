package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;

public class ControllerScanner {
    private final Reflections reflections;

    public ControllerScanner(Object... basePackages) {
        reflections = new Reflections(Arrays.asList(basePackages));
    }

    public Set<Class<?>> scan() {
        return reflections.getTypesAnnotatedWith(Controller.class);
    }
}
