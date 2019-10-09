package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        for (Class controller : controllerScanner.getControllers().keySet()) {
            Arrays.stream(controller.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> mappingHandlers(method, controllerScanner.getInstance(controller)));
        }
    }

    private void mappingHandlers(Method method, Object instance) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        HandlerExecution handlerExecution = new HandlerExecution(method, instance);
        List<RequestMethod> annotationRequestMethod = new ArrayList<>(Arrays.asList(annotation.method()));

        if (isRequestMethodEmpty(annotationRequestMethod)) {
            Arrays.stream(RequestMethod.values())
                    .filter(requestMethod -> isNotRegistered(annotation, requestMethod))
                    .forEach(requestMethod -> annotationRequestMethod.add(requestMethod));
        }

        annotationRequestMethod.stream()
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
        HandlerExecution handlerExecution = handlerExecutions.get(new HandlerKey(uri, method));
        return handlerExecution;
    }
}