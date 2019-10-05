package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Stream;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackages;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackages) {
        this.basePackages = basePackages;
    }

    public void initialize() {
        final ControllerScanner scanner = new ControllerScanner(basePackages);
        for (final Class<?> clazz : scanner.getClasses()) {
            final Object instance = scanner.getInstance(clazz);
            initHandlerExecutions(clazz, instance);
        }
    }

    private void initHandlerExecutions(final Class<?> clazz, final Object instance) {
        Stream.of(clazz.getMethods())
                .filter(x -> x.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    logger.debug("controller: {}, method: {}", clazz.getName(), method.getName());
                    final RequestMapping rm = method.getAnnotation(RequestMapping.class);
                    final RequestMethod[] requestMethods = rm.method().length == 0 ? RequestMethod.values() : rm.method();
                    for (final RequestMethod requestMethod : requestMethods) {
                        final HandlerKey handlerKey = new HandlerKey(rm.value(), requestMethod);
                        final HandlerExecution handlerExecution = (req, resp) -> (ModelAndView) method.invoke(instance, req, resp);
                        handlerExecutions.put(handlerKey, handlerExecution);
                    }
                });
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
