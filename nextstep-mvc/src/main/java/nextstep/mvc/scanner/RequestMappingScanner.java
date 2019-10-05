package nextstep.mvc.scanner;

import nextstep.web.annotation.RequestMapping;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

public class RequestMappingScanner {

    public static Set<Method> getMethods(Class<?> clazz) {
        return ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
    }
}
