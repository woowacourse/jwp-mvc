package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        for (Class<?> controllerReflection : collectControllerReflections()) {
            Object controller = createController(controllerReflection);

            Stream.of(controllerReflection.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> addHandlerExecution(controller, method));
        }
    }

    private Object createController(Class<?> controllerReflection) {
        try {
            return controllerReflection.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Set<Class<?>> collectControllerReflections() {
        return Stream.of(basePackage)
                .map(Reflections::new)
                .flatMap(reflections -> reflections.getTypesAnnotatedWith(Controller.class).stream())
                .collect(Collectors.toSet());
    }

    private void addHandlerExecution(Object controller, Method method) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);

        for (RequestMethod requestMethod : getRequestMethod(requestMapping)) {
            HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMethod);
            HandlerExecution handlerExecution = (req, res) -> (String) method.invoke(controller, req, res);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private RequestMethod[] getRequestMethod(RequestMapping requestMapping) {
        RequestMethod[] requestMethods = requestMapping.method();
        return requestMethods.length == 0 ? RequestMethod.values() : requestMethods;
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());
        HandlerKey handlerKey = new HandlerKey(requestURI, requestMethod);
        return handlerExecutions.get(handlerKey);
    }
}
