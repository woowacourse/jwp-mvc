package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        addController("/users", RequestMethod.POST);
        addController("/users", RequestMethod.GET);
        addController("/users/loginForm", RequestMethod.GET);
        addController("/users/login", RequestMethod.POST);
        addController("/users/logout", RequestMethod.GET);
    }

    private void addController(String requestUrl, RequestMethod requestMethod) {
        Set<Class<?>> controllers = ControllerScanner.scan(basePackage, Controller.class);

        for (Class<?> controller : controllers) {
            Method[] methods = controller.getMethods();
            matchRequest(methods, requestUrl, requestMethod).ifPresent(method -> handlerExecutions.put(
                    new HandlerKey(requestUrl, requestMethod),
                    new HandlerExecution(controller, method)));
        }
    }

    private Optional<Method> matchRequest(Method[] methods, String requestUrl, RequestMethod requestMethod) {
        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(RequestMapping.class)
                        && m.getAnnotation(RequestMapping.class).method().equals(requestMethod)
                        && m.getAnnotation(RequestMapping.class).value().equals(requestUrl))
                .findAny();
    }

    @Override
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
