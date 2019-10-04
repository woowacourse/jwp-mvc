package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationHandlerMapping {
    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        final Reflections reflections = new Reflections(basePackage);
        final Set<Class> annotated = new HashSet<>(reflections.getTypesAnnotatedWith(Controller.class));

        for (final Class cls : annotated) {
            registerRequestMappingClass(cls);
        }
    }

    private void registerRequestMappingClass(final Class cls) {
        for (final Method method : cls.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                registerHandler(method);
            }
        }
    }

    private void registerHandler(final Method method) {
        final List<HandlerKey> keys = getRequestMappingKeys(method);
        final HandlerExecution execution = new HandlerExecution(method);
        for (final HandlerKey key : keys) {
            handlerExecutions.put(key, execution);
        }
    }

    private List<HandlerKey> getRequestMappingKeys(final Method method) {
        final List<HandlerKey> result = new ArrayList<>();
        final RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        final RequestMethod[] methods = mapping.method().length == 0 ? RequestMethod.values() : mapping.method();
        for (final RequestMethod requestMethod : methods) {
            result.add(new HandlerKey(mapping.value(), requestMethod));
        }
        return result;
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
