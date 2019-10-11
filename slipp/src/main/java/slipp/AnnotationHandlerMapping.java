package slipp;

import com.google.common.collect.Maps;
import nextstep.mvc.handlermapping.HandlerMapping;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.HandlerKey;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.utils.ComponentScanner;
import nextstep.web.annotation.Controller;
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

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(nextstep.mvc.tobe.AnnotationHandlerMapping.class);

    private Object[] basePackages;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackages) {
        this.basePackages = basePackages;
    }

    public void initialize() {
        log.info("Initialized !");
        for (Object basePackage : basePackages) {
            if (!(basePackage instanceof String)) {
                log.error("not supported basePackage: {}", basePackage.getClass());
                return;
            }
            registerHandler((String) basePackage);
        }
    }

    private void registerHandler(String basePackagePrefix) {
        ComponentScanner componentScanner = ComponentScanner.fromBasePackagePrefix(basePackagePrefix);

        Map<Class<?>, Object> controllers = componentScanner.scan(Controller.class);
        for (final Class<?> controllerClass : controllers.keySet()) {
            log.info("controllerClass :{}", controllerClass);
            Arrays.asList(controllerClass.getDeclaredMethods()).stream()
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> {
                        HandlerKey handlerKey = makeHandlerKey(method);
                        HandlerExecution handlerExecution = makeHandlerExecution(method, controllers.get(controllerClass));

                        handlerExecutions.put(handlerKey, handlerExecution);
                    });
        }
        log.info("excutions: {}", handlerExecutions);
    }

    private HandlerKey makeHandlerKey(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        RequestMethod[] methods = requestMapping.method();
        return new HandlerKey(requestMapping.value(), (methods == null) ? null : methods[0]);
    }

    private HandlerExecution makeHandlerExecution(Method method, Object controller) {
        return (request, response) -> {
            try {
                return (ModelAndView) method.invoke(controller, request, response);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("e: ", e);
                return null;
            }
        };
    }

    public Optional<Object> getHandler(HttpServletRequest request) {
        log.debug("URI: {}", request.getRequestURI());
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));

        if (handlerExecutions.containsKey(handlerKey)) {
            return Optional.of(handlerExecutions.get(handlerKey));
        }

        HandlerKey emptyMethodHandlerKey = new HandlerKey(request.getRequestURI(), null);
        return Optional.ofNullable(handlerExecutions.get(emptyMethodHandlerKey));
    }
}

