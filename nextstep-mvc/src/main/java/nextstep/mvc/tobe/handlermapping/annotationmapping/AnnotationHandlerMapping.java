package nextstep.mvc.tobe.handlermapping.annotationmapping;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.handlermapping.ModelAndViewHandlerMapping;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.exception.DuplicateRequestMappingException;
import nextstep.mvc.tobe.exception.RenderFailedException;
import nextstep.utils.ClassUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements ModelAndViewHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> clazz : controllerClazz) {
            appendHandlerExecutions(clazz);
        }
    }

    private void appendHandlerExecutions(final Class<?> clazz) {
        Object newInstance = ClassUtils.createNewInstance(clazz);
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                List<HandlerKey> handlerKeys = createHandlerKeys(method);
                HandlerExecution handlerExecution = createHandlerExecution(newInstance, method);

                appendHandlerKeys(handlerKeys, handlerExecution);
            }
        }
    }

    private List<HandlerKey> createHandlerKeys(final Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String url = annotation.value();
        RequestMethod[] requestMethods = annotation.method();

        if (ArrayUtils.isEmpty(requestMethods)) {
            return createHandlerKeysWithRequestMethods(url, RequestMethod.values());
        }
        return createHandlerKeysWithRequestMethods(url, requestMethods);
    }

    private List<HandlerKey> createHandlerKeysWithRequestMethods(final String url, final RequestMethod[] requestMethods) {
        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(url, requestMethod))
                .collect(Collectors.toList());
    }

    private HandlerExecution createHandlerExecution(final Object newInstance, final Method method) {
        return (request, response) -> (ModelAndView) method.invoke(newInstance, request, response);
    }

    private void appendHandlerKeys(final List<HandlerKey> handlerKeys, final HandlerExecution handlerExecution) {
        for (HandlerKey handlerKey : handlerKeys) {
            checkDuplicateRequestMapping(handlerKey);
            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    private void checkDuplicateRequestMapping(final HandlerKey handlerKey) {
        if (handlerExecutions.containsKey(handlerKey)) {
            throw new DuplicateRequestMappingException();
        }
    }

    @Override
    public boolean handle(final HttpServletRequest req, final HttpServletResponse resp) {
        HandlerExecution execution = getHandler(req);
        if (doesNotExistsExecution(execution)) {
            return false;
        }

        try {
            ModelAndView modelAndView = execution.handle(req, resp);
            modelAndView.render(req, resp);
        } catch (Exception e) {
            throw new RenderFailedException();
        }
        return true;
    }

    private boolean doesNotExistsExecution(final HandlerExecution execution) {
        return Objects.isNull(execution);
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        HandlerKey handlerKey = new HandlerKey(requestURI, RequestMethod.valueOf(method));
        return handlerExecutions.get(handlerKey);
    }
}
