package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.Set;

public class ControllerScanner {
    public static Set<Class<?>> scan(Object[] packages) {
        Reflections reflections = new Reflections(packages);
        return reflections.getTypesAnnotatedWith(Controller.class);
    }
}
