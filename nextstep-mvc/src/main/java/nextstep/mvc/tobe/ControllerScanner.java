package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.exception.AnnotationScanFailException;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private final Reflections reflections;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public ControllerScanner(Reflections reflections) {
        this.reflections = reflections;
    }


    public Map<HandlerKey, HandlerExecution> scan() {
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
        Method[] methods = controller.getDeclaredMethods();
        Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
            .forEach(method -> addHandlerExcution(controllerInstance, method));
    }

    private void addHandlerExcution(Object controllerInstance, Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String url = annotation.value();
        RequestMethod[] requestMethods = annotation.method();

        addHandlerKey(controllerInstance, method, url, requestMethods);
    }

    private void addHandlerKey(Object controllerInstance, Method method, String url, RequestMethod[] requestMethods) {
        for (RequestMethod requestMethod : requestMethods) {
            HandlerKey key = new HandlerKey(url, requestMethod);
            handlerExecutions.put(key, (req, res) -> method.invoke(controllerInstance, req, res));
        }
    }
}
