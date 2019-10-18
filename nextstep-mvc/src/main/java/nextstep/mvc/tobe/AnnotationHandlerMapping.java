package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.exception.AnnotationScanFailException;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(new Reflections(basePackage));
        Set<Class<?>> controllers = controllerScanner.scan();
        try {
            initializeHandlerExecutions(controllers);
        } catch (Exception e) {
            throw new AnnotationScanFailException(e.getCause());
        }
        // TODO scan services
    }

    private void initializeHandlerExecutions(Set<Class<?>> controllers) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Class<?> controller : controllers) {
            Object controllerInstance = controller.getDeclaredConstructor().newInstance();
            RequestMappingScanner requestMappingScanner = new RequestMappingScanner(controller);
            Method[] methods = requestMappingScanner.scanMethods();
            initializeHandlerExecution(controllerInstance, methods);
        }
    }

    private void initializeHandlerExecution(Object controllerInstance, Method[] methods) {
        Arrays.stream(methods)
            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
            .forEach(method -> addHandlerExecution(controllerInstance, method));
    }

    private void addHandlerExecution(Object controllerInstance, Method method) {
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

    @Override
    public Object getHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());
        return handlerExecutions.get(new HandlerKey(url, method));
    }
}
