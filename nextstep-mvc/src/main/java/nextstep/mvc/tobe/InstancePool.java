package nextstep.mvc.tobe;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InstancePool {

    private Map<Class<?>, Object> instancePool;

    private InstancePool() {
        instancePool = new HashMap<>();
    }

    public static InstancePool getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final InstancePool INSTANCE = new InstancePool();
    }

    public void initPool(Class<? extends Annotation> annotation, Object... basePackage) {
        Map<Class<?>, Object> controllers = Scanner.scan(annotation, basePackage);
        controllers.keySet()
                .forEach(clazz -> instancePool.put(clazz, controllers.get(clazz)));
    }

    public Object instanceOf(Class<?> controller) {
        return instancePool.get(controller);
    }

    public Set<Class<?>> instancePoolKeySet() {
        return instancePool.keySet();
    }
}
