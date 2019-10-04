package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.exception.MappingException;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, nextstep.mvc.asis.Controller> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflection = new Reflections(basePackage);
        Set<Class<?>> controllers = reflection.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controller : controllers) {
            Method[] methods = controller.getDeclaredMethods();
            for (Method method : methods) {
                putHandlerExecution(controller, method);
            }
        }
    }

    @Override
    public nextstep.mvc.asis.Controller getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }

    private void putHandlerExecution(Class<?> controller, Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            String url = method.getAnnotation(RequestMapping.class).value();
            RequestMethod[] requestMethod = method.getAnnotation(RequestMapping.class).method();
            requestMethod = isRequestMethodEmpty(requestMethod);

            HandlerKey handlerKey = new HandlerKey(url, requestMethod[0]);

            if (handlerExecutions.containsKey(handlerKey)) {
                throw new MappingException();
            }

            handlerExecutions.put(handlerKey,
                    (request, response) -> method.invoke(controller.newInstance(), request, response));
        }
    }

    private RequestMethod[] isRequestMethodEmpty(RequestMethod[] requestMethod) {
        if (requestMethod.length == 0) {
            requestMethod = RequestMethod.values();
        }
        return requestMethod;
    }
}
