package nextstep.mvc.tobe.mapping;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.utils.PathPatternUtils;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
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
        for (final Class<?> clazz : scanner.keySet()) {
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
                    final RequestMethod[] requestMethods = rm.method();
                    final HandlerExecution handlerExecution = new HandlerExecution(instance, method);
                    for (final RequestMethod requestMethod : requestMethods) {
                        final HandlerKey handlerKey = new HandlerKey(rm.value(), requestMethod);
                        handlerExecutions.put(handlerKey, handlerExecution);
                    }
                });
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        final String requestUri = request.getRequestURI();
        final RequestMethod rm = RequestMethod.valueOf(request.getMethod());
        final Optional<HandlerKey> handlerKey = handlerExecutions.keySet().stream()
                .filter(key -> {
                    final PathPattern pathPattern = PathPatternUtils.parse(key.getUrl());
                    return pathPattern.matches(PathPatternUtils.toPathContainer(requestUri));
                })
                .findAny();

        return handlerKey
                .map(key -> handlerExecutions.get(new HandlerKey(key.getUrl(), rm)))
                .orElse(null);
    }
}
