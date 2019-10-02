package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.exception.DuplicateRequestMappingException;
import nextstep.mvc.tobe.exception.RequestUrlNotFoundException;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
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

                checkDuplicateRequestMapping(handlerKey);
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

    private void checkDuplicateRequestMapping(final HandlerKey handlerKey) {
        if (handlerExecutions.containsKey(handlerKey)) {
            throw new DuplicateRequestMappingException();
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        HandlerKey handlerKey = new HandlerKey(requestURI, RequestMethod.valueOf(method));
        if (doesNotExistsKey(handlerKey)) {
            handlerKey = findHandlerKeyByUrl(requestURI);
        }
        return handlerExecutions.get(handlerKey);
    }

    private boolean doesNotExistsKey(final HandlerKey handlerKey) {
        return Objects.isNull(handlerExecutions.get(handlerKey));
    }

    private HandlerKey findHandlerKeyByUrl(final String requestURI) {
        return handlerExecutions.keySet().stream()
                .filter(handlerKey -> handlerKey.isSameUrl(requestURI))
                .findFirst()
                .orElseThrow(RequestUrlNotFoundException::new);
    }
}
