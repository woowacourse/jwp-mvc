package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.RequestMapping;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class AnnotationHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Arrays.stream(basePackage).forEach(basePackage -> {
            mappingHandler(new Reflections(basePackage, new MethodAnnotationsScanner()));
        });
    }

    private void mappingHandler(Reflections reflections) {
        reflections.getMethodsAnnotatedWith(RequestMapping.class).stream()
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(this::mapping);
    }

    private void mapping(Method method) {
        RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
        HandlerKey handlerKey = new HandlerKey(mapping.value(), mapping.method());
        HandlerExecution handlerExecution;
        try {
            handlerExecution = new HandlerExecution(method, method.getDeclaringClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            logger.debug(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        handlerExecutions.put(handlerKey, handlerExecution);
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request));
    }
}
