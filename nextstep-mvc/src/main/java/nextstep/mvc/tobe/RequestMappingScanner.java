package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RequestMappingScanner {
    private final Class<?> controller;

    public RequestMappingScanner(Class<?> controller) {
        this.controller = controller;
    }

    public Method[] scanMethods() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return controller.getDeclaredMethods();
    }
}
