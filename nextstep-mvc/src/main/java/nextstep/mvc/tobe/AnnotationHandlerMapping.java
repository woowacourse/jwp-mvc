package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        scanControllers(reflections);
        // TODO scan services
    }

    private void scanControllers(Reflections reflections) {
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        try {
            for (Class<?> controller : controllers) {
                findHandlersInController(controller);
            }
        } catch (Exception e) {
            throw new AnnotationScanFailException(e.getCause());
        }
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
        RequestMethod requestMethod = annotation.method();
        HandlerKey key = new HandlerKey(url, requestMethod);
        handlerExecutions.put(key, (req, res) -> (ModelAndView) method.invoke(controllerInstance, req, res));
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());
        HandlerExecution handler = handlerExecutions.get(new HandlerKey(url, method));

        if(handler == null) {
            throw new HandlerNotFoundException();
        }

        return handler;
    }
}
