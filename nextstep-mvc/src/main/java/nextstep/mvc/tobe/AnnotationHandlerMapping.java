package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private ComponentScanner componentScanner;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.componentScanner = ComponentScanner.of(basePackage);
    }

    @Override
    public void initialize() {
        Set<Method> methods = componentScanner.getRequestMappingMethods();
        logger.info("{} Controllers are added by Annotation.", methods.size());
        methods.forEach(this::mapControllerMethod);
    }

    private void mapControllerMethod(Method method) {
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] methods = mapping.method();
        if (mapping.method().length == 0) {
            methods = RequestMethod.values();
        }
        Arrays.stream(methods)
                .map(requestMethod -> new HandlerKey(mapping.value(), requestMethod))
                .forEach(key -> handlerExecutions.put(key, executeController(method, componentScanner.instanceFromMethod(method))));
    }

    private HandlerExecution executeController(Method method, Object instance) {
        return (request, response) -> {
            try {
                return (ModelAndView) method.invoke(instance, request, response);
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.error("Error occurred while handle request", e);
                throw new HandlerMappingException(e);
            }
        };
    }

    @Override
    public Optional<HandlerExecution> getHandler(HttpServletRequest request) {
        return Optional.of(handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()))));
    }
}
