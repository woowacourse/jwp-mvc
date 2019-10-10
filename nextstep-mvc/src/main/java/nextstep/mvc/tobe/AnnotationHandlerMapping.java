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
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Set<Class<?>> controllers = ControllerScanner.scan(basePackage, Controller.class);

        for (Class<?> controller : controllers) {
            Arrays.stream(controller.getMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> registerHandler(controller, method));
        }
    }

    private void registerHandler(Class<?> controller, Method method) {
        RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();
        String requestUrl = method.getAnnotation(RequestMapping.class).value();

        if (isEmpty(requestMethods)) {
            Arrays.stream(RequestMethod.values()).forEach(requestMethod -> handlerExecutions.put(
                    new HandlerKey(requestUrl, requestMethod),
                    new HandlerExecution(controller, method)));
            return;
        }
        handlerExecutions.put(new HandlerKey(requestUrl, requestMethods[0]), new HandlerExecution(controller, method));
    }


    private boolean isEmpty(RequestMethod[] requestMethods) {
        return requestMethods.length == 0;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());

        HandlerKey handlerKey = new HandlerKey(uri, method);
        if (handlerExecutions.containsKey(handlerKey)) {
            return handlerExecutions.get(handlerKey);
        }
        // 404 에러
        throw new IllegalArgumentException("404");
    }
}
