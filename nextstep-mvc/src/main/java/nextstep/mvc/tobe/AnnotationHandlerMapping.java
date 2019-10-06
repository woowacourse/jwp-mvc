package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class controller : controllers) {
            Arrays.stream(controller.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(this::createHandlerMapping);
        }
    }

    private void createHandlerMapping(Method controllerMethod) {
        RequestMapping annotation = controllerMethod.getAnnotation(RequestMapping.class);
        HandlerExecution execution = createHandlerExecutionOf(controllerMethod);
        RequestMethod[] requestMethods = annotation.method();

        if (requestMethods.length == 0) {
            connectHandlerToAllConnectableRequestMethods(annotation, execution);
            return;
        }

        connectHandler(annotation, execution);
    }

    private HandlerExecution createHandlerExecutionOf(Method method) {
        Class controller = method.getDeclaringClass();
        return new HandlerExecution(controller, method);
    }

    private void connectHandlerToAllConnectableRequestMethods(RequestMapping annotation,
                                                              HandlerExecution execution) {
        String uri = annotation.value();
        List<RequestMethod> connectable = Arrays.stream(RequestMethod.values())
                .filter(requestMethod -> isConnectable(new HandlerKey(uri, requestMethod)))
                .collect(Collectors.toList());

        HandlerKey handlerKey;
        for (RequestMethod requestMethod : connectable) {
            handlerKey = new HandlerKey(uri, requestMethod);
            handlerExecutions.put(handlerKey, execution);
        }
    }

    private boolean isConnectable(HandlerKey handlerKey) {
        return !handlerExecutions.containsKey(handlerKey);
    }

    private void connectHandler(RequestMapping annotation, HandlerExecution execution) {
        String uri = annotation.value();

        for (RequestMethod requestMethod : annotation.method()) {
            HandlerKey handlerKey = new HandlerKey(uri, requestMethod);
            handlerExecutions.put(handlerKey, execution);
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());
        HandlerExecution handlerExecution = handlerExecutions.get(new HandlerKey(uri, method));

        checkNull(handlerExecution);
        return handlerExecution;
    }

    private void checkNull(HandlerExecution handlerExecution) {
        if (handlerExecution == null) {
            throw new NotFoundHandlerException();
        }
    }
}
