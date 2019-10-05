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

public class RequestMappingScanner {

    private final ComponentScanner componentScanner;
    private final Map<Class<?>, Object> instances;

    public RequestMappingScanner(ComponentScanner componentScanner) {
        this.componentScanner = componentScanner;
        instances = new HashMap<>();
    }

    public Set<Method> getRequestMappingMethods() {
        return componentScanner.getClassesAnnotated(Controller.class).stream()
                .peek(cls -> instances.put(cls, instantiate(cls)))
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class)))
                .collect(Collectors.toSet());
    }

    public Object instanceFromMethod(Method method) {
        return instances.get(method.getDeclaringClass());
    }

    private Object instantiate(Class<?> cls) {
        try {
            return cls.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new ComponentScanException(e);
        }
    }
}
