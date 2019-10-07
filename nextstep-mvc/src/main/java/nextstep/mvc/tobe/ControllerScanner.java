package nextstep.mvc.tobe;

import nextstep.mvc.tobe.exception.AnnotationScanFailException;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    private final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

    Map<HandlerKey, HandlerExecution> scan(Reflections reflections) {
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        try {
            for (Class<?> controller : controllers) {
                findHandlersInController(controller);
            }
        } catch (Exception e) {
            throw new AnnotationScanFailException(e.getCause());
        }
        return handlerExecutions;
    }

    private void findHandlersInController(Class<?> controller) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object controllerInstance = controller.getDeclaredConstructor().newInstance();
        logger.debug("c : {}", controller);
        Method[] methods = controller.getDeclaredMethods();
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> addHandlerExecution(controllerInstance, method));
    }

    private void addHandlerExecution(Object controllerInstance, Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String url = annotation.value();
        RequestMethod[] requestMethods = annotation.method();
        for (RequestMethod rm : requestMethods) {
            HandlerKey key = new HandlerKey(url, rm);
            handlerExecutions.put(key, (req, res) -> (ModelAndView) method.invoke(controllerInstance, req, res));
        }
    }
}
