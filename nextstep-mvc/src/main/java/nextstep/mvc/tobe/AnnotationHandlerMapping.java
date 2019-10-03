package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.RequestMapping;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            mappingMethod(new Reflections(basePackage, new MethodAnnotationsScanner()));
        });
    }

    private void mappingMethod(Reflections reflections) {
        reflections.getMethodsAnnotatedWith(RequestMapping.class).stream()
                .filter(method -> Arrays.stream(method.getParameterTypes())
                        .allMatch(x -> x.equals(HttpServletRequest.class) || x.equals(HttpServletResponse.class)))
                .forEach(method -> {
                    RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
                    HandlerExecution handlerExecution = new HandlerExecution();
                    handlerExecution.setMethod(method);

                    try {
                        handlerExecution.setTarget(method.getDeclaringClass().newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        logger.debug(e.getMessage());
                    }
                    handlerExecutions.put(HandlerKey.of(mapping.value(), mapping.method()), handlerExecution);
                });
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(HandlerKey.of(request));
    }
}
