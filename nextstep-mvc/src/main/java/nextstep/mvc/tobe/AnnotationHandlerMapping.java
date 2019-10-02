package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        log.info("Initialized Request Mapping!");

        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        controllers.stream()
                .flatMap(controller -> Arrays.stream(controller.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(this::putHandlerExecutions);
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.resolve(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }

    private void putHandlerExecutions(Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        annotation.method().getMethods().forEach(requestMethod -> {
            HandlerKey handlerKey = new HandlerKey(annotation.value(), requestMethod);
            HandlerExecution handlerExecution = new HandlerExecution((method));
            handlerExecutions.put(handlerKey, handlerExecution);
            log.info("HandlerKey : {}, HandlerExecution : {}", handlerKey, handlerExecution);
        });
    }
}
