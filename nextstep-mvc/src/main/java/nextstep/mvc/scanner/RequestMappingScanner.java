package nextstep.mvc.scanner;

import nextstep.web.annotation.RequestMapping;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

public class RequestMappingScanner {

    private Set<Method> methods;

    public RequestMappingScanner(final Class<?> clazz) {
        this.methods = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
    }

    public Set<Method> getMethods() {
        return methods;
    }
}
