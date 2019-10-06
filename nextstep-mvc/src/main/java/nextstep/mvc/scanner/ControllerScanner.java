package nextstep.mvc.scanner;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {

    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
    private final Map<Class<?>, Object> instanceOfClazz = new HashMap<>();

    public ControllerScanner(final Object[] basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);
        controllerClazz.forEach(clazz -> {
            try {
                instanceOfClazz.put(clazz, clazz.getConstructor().newInstance());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    public Object getInstance(Class<?> clazz) {
        return instanceOfClazz.get(clazz);
    }

    public Map<Class<?>, Object> getInstanceOfClazz() {
        return instanceOfClazz;
    }
}
