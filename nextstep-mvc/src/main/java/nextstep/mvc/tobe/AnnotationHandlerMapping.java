package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        for (Class controller : controllerScanner.getControllers().keySet()) {
            Arrays.stream(controller.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> mappingHandlers(method, controllerScanner.getInstance(controller)));
        }
    }

    private void mappingHandlers(Method method, Object instance) {
        RequestMapping rm = method.getAnnotation(RequestMapping.class);
        HandlerExecution handlerExecution = new HandlerExecution(method, instance);
        if (rm.method().length == 0) {
            mappingEmptyMethodHandler(rm, handlerExecution);
            return;
        }

        for (int i = 0; i < rm.method().length; i++) {
            HandlerKey handlerKey = new HandlerKey(rm.value(), rm.method()[i]);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private void mappingEmptyMethodHandler(RequestMapping rm, HandlerExecution handlerExecution) {
        List<RequestMethod> requestMethods = Arrays.stream(RequestMethod.values())
                .filter(requestMethod -> notExists(new HandlerKey(rm.value(), requestMethod)))
                .collect(Collectors.toList());
        for (RequestMethod requestMethod : requestMethods) {
            HandlerKey handlerKey = new HandlerKey(rm.value(), requestMethod);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private boolean notExists(HandlerKey handlerKey) {
        return handlerExecutions.get(handlerKey) == null;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());
        HandlerExecution handlerExecution = handlerExecutions.get(new HandlerKey(uri, method));

        isEmpty(handlerExecution);
        return handlerExecution;
    }

    private void isEmpty(HandlerExecution handlerExecution) {
        if (handlerExecution == null) {
            throw new NotFoundHandlerException();
        }
    }
}
