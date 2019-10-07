package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class RequestMappingScanner {

    private final ComponentScanner componentScanner;

    RequestMappingScanner(ComponentScanner componentScanner) {
        this.componentScanner = componentScanner;
    }

    Set<Method> getRequestMappingMethods() {
        return componentScanner.getClassesAnnotated(Controller.class).stream()
                .peek(Instances::putIfAbsent)
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class)))
                .collect(Collectors.toSet());
    }
}
