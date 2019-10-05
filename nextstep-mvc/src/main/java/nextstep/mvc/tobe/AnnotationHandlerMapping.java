package nextstep.mvc.tobe;

import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private static final int EMPTY = 0;

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        new ComponentScan(new Reflections(basePackage))
                .getRequestMappingAnnotationPresentMethod()
                .forEach(this::registerHandler);

        handlerExecutions.keySet()
                .forEach(key -> logger.info("Path : {}, Controller : {}", key, handlerExecutions.get(key).getClass()));
    }

    private void registerHandler(Method method) {
        final RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        final RequestMethod[] requestMethods = getSupportMethod(mapping);

        Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(mapping.value(), requestMethod))
                .forEach(handlerKey -> handlerExecutions.put(handlerKey, new HandlerExecution(method)));
    }

    private RequestMethod[] getSupportMethod(RequestMapping mapping) {
        return mapping.method().length == EMPTY ? RequestMethod.values() : mapping.method();
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return handlerExecutions
                .get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())))
                .handle(request, response);
    }
}
