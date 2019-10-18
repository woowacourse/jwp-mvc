package nextstep.mvc.tobe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestMappingScanner {
    private final Class<?> controller;

    public RequestMappingScanner(Class<?> controller) {
        this.controller = controller;
    }

    public Method[] scanMethods() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return controller.getDeclaredMethods();
    }
}
