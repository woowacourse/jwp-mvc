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
        HandlerKey handlerKey = new HandlerKey(annotation.value(), annotation.method());
        HandlerExecution handlerExecution = new HandlerExecution(controller, method);

        handlerExecutions.put(handlerKey, handlerExecution);
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());

        HandlerKey handlerKey = new HandlerKey(uri, method);
        if (handlerExecutions.containsKey(handlerKey)) {
            return handlerExecutions.get(handlerKey);
        }
        throw new IllegalArgumentException();
    }
}
