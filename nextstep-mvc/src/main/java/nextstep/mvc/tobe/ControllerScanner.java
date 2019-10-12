package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.Set;

public class ControllerScanner {
    public static Set<Class<?>> scan(Object[] basePackage, Class<Controller> controllerClass) {
        Reflections reflections = new Reflections(basePackage);

        return reflections.getTypesAnnotatedWith(controllerClass);
    }
}
