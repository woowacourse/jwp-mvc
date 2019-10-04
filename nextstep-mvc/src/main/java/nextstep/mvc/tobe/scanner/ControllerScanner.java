package nextstep.mvc.tobe.scanner;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    public static Map<Class<?>, Object> scan(Object... basePackage) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        Map<Class<?>, Object> controllerInstanceMap = new HashMap<>();
        for (final Class<?> controller : controllers) {
            controllerInstanceMap.put(controller, controller.getDeclaredConstructor().newInstance());
        }
        return controllerInstanceMap;
    }
}
