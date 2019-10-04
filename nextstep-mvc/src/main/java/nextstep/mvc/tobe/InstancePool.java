package nextstep.mvc.tobe;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InstancePool {

    private static Map<Class<?>, Object> instancePool;

    static {
        instancePool = new HashMap<>();
    }

    public static void initPool(Class<? extends Annotation> annotation, Object... basePackage) {
        Map<Class<?>, Object> controllers = Scanner.scan(annotation, basePackage);
        controllers.keySet()
                .forEach(aClass -> instancePool.put(aClass, controllers.get(aClass)));
    }

    public static Object getInstance(Class<?> controller) {
        return instancePool.get(controller);
    }

    public static Set<Class<?>> instancePoolKeySet() {
        return instancePool.keySet();
    }
}
