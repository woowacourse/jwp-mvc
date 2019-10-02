package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class ControllerScanner {

    private Map<Class<?>, Object> controllers = Maps.newHashMap();

    public ControllerScanner(Object... basePackage) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> aClass : typesAnnotatedWith) {
            this.controllers.put(aClass, aClass.newInstance());
        }
    }

    public Set<Class<?>> getKeys() {
        return controllers.keySet();
    }

    public Object getController(Class<?> key) {
        return controllers.get(key);
    }
}
