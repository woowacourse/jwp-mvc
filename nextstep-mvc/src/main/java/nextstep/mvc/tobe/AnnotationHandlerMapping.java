package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.exception.MappingException;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

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

    private void putHandlerExecution(Class<?> controller, Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            String url = method.getAnnotation(RequestMapping.class).value();
            RequestMethod requestMethod = method.getAnnotation(RequestMapping.class).method();
            if (requestMethod.isAll()) {
                putAllMethodMapping(controller, method, url);
                return;
            }
            HandlerKey handlerKey = new HandlerKey(url, requestMethod);
            handlerExecutions.put(handlerKey,
                    (request, response) -> (ModelAndView) method.invoke(controller.newInstance(), request, response));
        }
    }

    private void putAllMethodMapping(Class<?> controller, Method method, String url) {
        for (RequestMethod value : RequestMethod.values()) {
            HandlerKey handlerKey = new HandlerKey(url, value);
            if (handlerExecutions.containsKey(handlerKey)) {
                throw new MappingException();
            }
            handlerExecutions.put(handlerKey,
                    (request, response) -> (ModelAndView) method.invoke(controller.newInstance(), request, response));
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
