package nextstep.mvc.tobe.handlermapping;

import com.google.common.collect.Maps;
import nextstep.mvc.Handler;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.exception.DuplicateHandlerKeyException;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        controllers.forEach(this::handlerMappingForMethodsOf);
    }

    private void handlerMappingForMethodsOf(Class<?> controllerClass, Object controllerInstance) {
        Set<Method> annotatedMethods = getAllMethods(controllerClass, withAnnotation(RequestMapping.class));
        annotatedMethods.forEach(method -> handlerMapping(method, controllerInstance));
    }

    private void handlerMapping(Method method, Object controller) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] requestMethods = requestMapping.method();

        if (requestMethods.length != 0) {
            handlerMappingForRequestMethod(requestMethods, method, controller, requestMapping);
        } else {
            handlerMappingForRequestMethod(RequestMethod.values(), method, controller, requestMapping);
        }
    }

    private void handlerMappingForRequestMethod(RequestMethod[] requestMethods,
                                                Method method,
                                                Object controller,
                                                RequestMapping requestMapping) {
        Arrays.stream(requestMethods)
                .forEach(requestMethod -> handlerExecutions.put(createHandlerKey(requestMapping.value(), requestMethod),
                            (request, response) -> (ModelAndView) method.invoke(controller, request, response)));
    }

    private HandlerKey createHandlerKey(String url, RequestMethod requestMethod) {
        HandlerKey handlerKey = new HandlerKey(url, requestMethod);
        validDuplicateHandlerKey(handlerKey);

        return handlerKey;
    }

    @Override
    public boolean support(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        return handlerExecutions.containsKey(createHandlerKey(requestUrl, requestMethod));
    }

    @Override
    public Handler getHandler(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());
        return handlerExecutions.get(createHandlerKey(requestUrl, requestMethod));
    }

    private void validDuplicateHandlerKey(HandlerKey handlerKey) {
        if (handlerExecutions.containsKey(handlerKey)) {
            throw new DuplicateHandlerKeyException();
        }
    }
}
