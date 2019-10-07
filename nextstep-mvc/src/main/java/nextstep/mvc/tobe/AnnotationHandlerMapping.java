package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner.scan(basePackage, Controller.class).forEach(controller ->
                Arrays.stream(controller.getMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                        .forEach(method -> putHandlerExecution(controller, method))
        );
    }

    private void putHandlerExecution(Class<?> controller, Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        List<RequestMethod> requestMethods = Arrays.asList(annotation.method());

        if (requestMethods.isEmpty()) {
            requestMethods = Arrays.asList(RequestMethod.values());
        }

        requestMethods.forEach(requestMethod -> {
            HandlerKey handlerKey = new HandlerKey(annotation.value(), requestMethod);
            HandlerExecution handlerExecution = new HandlerExecution(controller, method);

            handlerExecutions.put(handlerKey, handlerExecution);
        });
    }

    public static HandlerKey createKey(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());

        return new HandlerKey(uri, method);
    }

    @Override
    public boolean containsKey(HttpServletRequest request) {
        HandlerKey handlerKey = createKey(request);

        return handlerExecutions.containsKey(handlerKey);
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = createKey(request);

        return handlerExecutions.get(handlerKey);
    }
}
