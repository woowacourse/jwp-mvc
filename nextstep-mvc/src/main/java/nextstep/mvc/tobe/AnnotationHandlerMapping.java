package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
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
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> clazz : controllerClazz) {
            appendHandlerExecutions(clazz);
        }
    }

    private void appendHandlerExecutions(final Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                HandlerKey handlerKey = createHandlerKey(method);

                HandlerExecution handlerExecution
                        = (request, response) -> (ModelAndView) method.invoke(clazz.newInstance(), request, response);
                handlerExecutions.put(handlerKey, handlerExecution);
            }
        }
    }

    private HandlerKey createHandlerKey(final Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String url = annotation.value();
        RequestMethod requestMethod = annotation.method();
        return new HandlerKey(url, requestMethod);
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        HandlerKey handlerKey = new HandlerKey(requestURI, RequestMethod.valueOf(method));
        return handlerExecutions.get(handlerKey);
    }
}
