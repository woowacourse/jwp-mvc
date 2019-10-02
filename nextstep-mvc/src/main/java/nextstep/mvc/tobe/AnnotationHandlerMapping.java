package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        addController("/users", RequestMethod.POST);
        addController("/users", RequestMethod.GET);
    }

    private void addController(String requestUrl, RequestMethod requestMethod) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controller : controllers) {
            applyAnnotationMethod(requestUrl, requestMethod, controller);
        }
    }

    private void applyAnnotationMethod(String requestUrl, RequestMethod requestMethod, Class<?> controller) {
        Method[] methods = controller.getDeclaredMethods();
        for (Method method : methods) {
            processRequestMappingAnnotation(requestUrl, requestMethod, controller, method);
        }
    }

    private void processRequestMappingAnnotation(String requestUrl, RequestMethod requestMethod, Class<?> controller, Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            setHandlerExecution(requestUrl, requestMethod, controller, method, annotation);
        }
    }

    private void setHandlerExecution(String requestUrl, RequestMethod requestMethod, Class<?> controller, Method method, RequestMapping annotation) {
        if (requestUrl.equals(annotation.value()) && requestMethod.equals(annotation.method())) {
            handlerExecutions.put(new HandlerKey(requestUrl, requestMethod), new HandlerExecution(controller, method));
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.of(request.getMethod());

        HandlerKey handlerKey = new HandlerKey(uri, method);
        if (handlerExecutions.containsKey(handlerKey)) {
            return handlerExecutions.get(handlerKey);
        }
        throw new IllegalArgumentException();
    }
}
