package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
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

    public void initialize() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class controller : controllers) {
            Arrays.stream(controller.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> mappingHandlers(method));
        }
    }

    private void mappingHandlers(Method method) {
        RequestMapping rm = method.getAnnotation(RequestMapping.class);
        HandlerExecution handlerExecution = new HandlerExecution(method.getDeclaringClass(), method);
        if (rm.method() != null) {
            HandlerKey handlerKey = new HandlerKey(rm.value(), rm.method()[0]);
            handlerExecutions.put(handlerKey, handlerExecution);
            return;
        }
        mappingEmptyMethodHandler(rm, handlerExecution);
    }

    private void mappingEmptyMethodHandler(RequestMapping rm, HandlerExecution handlerExecution) {
        List<RequestMethod> requestMethods = Arrays.stream(RequestMethod.values())
                .filter(requestMethod -> isDuplicated(new HandlerKey(rm.value(), requestMethod)))
                .collect(Collectors.toList());

        for (RequestMethod requestMethod : requestMethods) {
            HandlerKey handlerKey = new HandlerKey(rm.value(), requestMethod);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private boolean isDuplicated(HandlerKey handlerKey) {
        return handlerExecutions.get(handlerKey) == null;
    }

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
