package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.support.AnnotationApplicationContext;
import nextstep.mvc.tobe.support.ApplicationContext;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

public class AbstractHandlerMapping implements HandlerMapping {
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    @Override
    public void initialize(AnnotationApplicationContext context) {
        context.scanBeans(Controller.class);
        Map<Class<?>, Object> controllers = context.getBeans();
        for (Class controller : controllers.keySet()) {
            Arrays.stream(controller.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> mappingHandlers(method, context.getInstance(controller)));
        }
    }

    private void mappingHandlers(Method method, Object instance) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        HandlerExecution handlerExecution = new HandlerExecution(method, instance);
        List<RequestMethod> annotationRequestMethod = new ArrayList<>(Arrays.asList(annotation.method()));

        if (isRequestMethodEmpty(annotationRequestMethod)) {
            Arrays.stream(RequestMethod.values())
                    .filter(requestMethod -> isNotRegistered(annotation, requestMethod))
                    .forEach(annotationRequestMethod::add);
        }
        annotationRequestMethod
                .forEach(x -> {
                    HandlerKey handlerKey = new HandlerKey(annotation.value(), x);
                    handlerExecutions.put(handlerKey, handlerExecution);
                });
    }

    private boolean isRequestMethodEmpty(List<RequestMethod> annotationRequestMethod) {
        return annotationRequestMethod.size() == 0;
    }

    private boolean isNotRegistered(RequestMapping annotation, RequestMethod requestMethod) {
        return !handlerExecutions.containsKey(new HandlerKey(annotation.value(), requestMethod));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());
        return handlerExecutions.get(new HandlerKey(uri, method));
    }
}