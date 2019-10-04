package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;

import java.util.Map;
import java.util.Set;

public class InstancePool {

    private static Map<Class<?>, Object> controllerInstancePool;

    public static void initControllerPoll(Object... basePackage) {
        controllerInstancePool = Scanner.scan(Controller.class, basePackage);
    }

    public static Object getInstance(Class<?> controller) {
        return controllerInstancePool.get(controller);
    }

    public static Set<Class<?>> controllerInstancePoolKeySet() {
        return controllerInstancePool.keySet();
    }
}
