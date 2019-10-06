package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.AnnotationScanner;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        log.info("Initialized Request Mapping!");
        Map<Class<?>, Object> mapping = AnnotationScanner.scan(Controller.class, basePackage);

        mapping.keySet().stream()
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> putHandlerExecutions(method, mapping.get(method.getDeclaringClass())));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.resolve(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }

    private void putHandlerExecutions(Method method, Object instance) {
        HandlerExecution handlerExecution = new HandlerExecution(method, instance);
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);

        Arrays.stream(annotation.method())
                .map(requestMethod -> new HandlerKey(annotation.value(), requestMethod))
                .forEach(handlerKey -> {
                    handlerExecutions.put(handlerKey, handlerExecution);
                    log.info("HandlerKey : {}, HandlerExecution : {}", handlerKey, handlerExecution);
                });
    }
}
