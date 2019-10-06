package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.scanner.ControllerScanner;
import nextstep.mvc.scanner.RequestMappingScanner;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);

        for (Map.Entry<Class<?>, Object> entry : controllerScanner.getInstanceOfClazz().entrySet()) {
            Class clazz = entry.getKey();
            Object instance = entry.getValue();

            RequestMappingScanner requestMappingScanner = new RequestMappingScanner(clazz);
            Set<Method> methods = requestMappingScanner.getMethods();
            putExecutionsByMethod(instance, methods);
        }
    }

    private void putExecutionsByMethod(final Object instance, final Set<Method> methods) {
        for (Method method : methods) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            putHandlerExecutions(instance, method, requestMapping);
        }
    }

    private void putHandlerExecutions(final Object instance, final Method method, final RequestMapping requestMapping) {
        for (RequestMethod requestMethod : requestMapping.method()) {
            HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMethod);
            handlerExecutions.put(handlerKey, new HandlerExecution(instance, method));
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        log.debug("URI: {}", request.getRequestURI());
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
