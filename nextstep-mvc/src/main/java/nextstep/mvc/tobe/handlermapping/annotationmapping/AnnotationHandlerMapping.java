package nextstep.mvc.tobe.handlermapping.annotationmapping;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.ControllerScanner;
import nextstep.mvc.tobe.exception.DuplicateRequestMappingException;
import nextstep.mvc.tobe.handlermapping.HandlerMapping;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private final Object[] basePackage;

    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private final ControllerScanner controllerScanner = new ControllerScanner();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        controllerScanner.scan(basePackage);

        for (Method method : controllerScanner.getAllDeclaredMethods()) {
            appendHandlerExecutions(method);
        }
    }

    private void appendHandlerExecutions(final Method method) {
        Class<?> clazz = method.getDeclaringClass();
        Object newInstance = controllerScanner.get(clazz);

        if (method.isAnnotationPresent(RequestMapping.class)) {
            List<HandlerKey> handlerKeys = createHandlerKeys(method);
            HandlerExecution handlerExecution = createHandlerExecution(newInstance, method);

            appendHandlerKeys(handlerKeys, handlerExecution);
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
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        HandlerKey handlerKey = new HandlerKey(requestURI, RequestMethod.valueOf(method));
        return handlerExecutions.get(handlerKey);
    }
}
