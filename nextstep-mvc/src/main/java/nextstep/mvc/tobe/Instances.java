package nextstep.mvc.tobe;

import nextstep.mvc.tobe.exception.InstanceManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class Instances {

    private static final Logger logger = LoggerFactory.getLogger(Instances.class);
    private static final Map<Class, Object> instances = new HashMap<>();

    static void putIfAbsent(Class<?> clazz) {
        instances.computeIfAbsent(clazz, cls -> instantiate(clazz));
    }

    private static Object instantiate(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Error while putting instance in", e);
            throw new InstanceManagerException("Error while putting instance in", e);
        }
    }

    static Object getFromMethod(Method method) {
        return instances.get(method.getDeclaringClass());
    }
}
