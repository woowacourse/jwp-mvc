package nextstep.mvc.tobe.mapping;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.handler.HandlerKey;
import nextstep.mvc.tobe.support.ControllerScanner;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private final ControllerScanner scanner;

    public AnnotationHandlerMapping(final ControllerScanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void initialize() {
        Set<Class<?>> controllers = scanner.getControllers();

        for (final Class clazz : controllers) {
            List<Method> methods = generateMethods(clazz);
            methods.forEach(method -> addHandlerExecutions(clazz, method));
        }
        handlerExecutions.forEach((key, value) -> logger.debug("handlerExecutions key : {}, value : {}", key, value));
    }

    private List<Method> generateMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toList());
    }

    private void addHandlerExecutions(Class<?> clazz, Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String url = requestMapping.value()
                .trim();
        RequestMethod[] requestMethods = requestMapping.method();
        Object instance = scanner.instantiate(clazz);

        if (requestMethods.length == 0) {
            requestMethods = RequestMethod.values();
        }

        for (final RequestMethod requestMethod : requestMethods) {
            handlerExecutions.put(new HandlerKey(url, requestMethod), new HandlerExecution(instance, method));
        }
    }

    @Override
    @Nullable
    public HandlerExecution getHandler(HttpServletRequest request) {
        final RequestMethod method = RequestMethod.valueOf(request.getMethod());
        final HandlerKey key = new HandlerKey(request.getRequestURI(), method);
        return handlerExecutions.get(key);
    }
}
