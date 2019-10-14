package nextstep.mvc.tobe.mapping;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.exception.NotMatchHandlerKeyException;
import nextstep.mvc.tobe.argumentresolver.HandlerMethodArgumentResolverManager;
import nextstep.mvc.tobe.exception.MappingException;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner scanner = new ControllerScanner(basePackage);
        Set<Class<?>> controllers = scanner.getControllers();

        for (Class<?> controller : controllers) {
            Object newInstance = scanner.getController(controller);
            Set<Method> methods = ReflectionUtils.getAllMethods(controller,
                    ReflectionUtils.withAnnotation(RequestMapping.class));

            for (Method method : methods) {
                String url = method.getAnnotation(RequestMapping.class).value();
                List<RequestMethod> requestMethods =
                        Arrays.asList(method.getAnnotation(RequestMapping.class).method());

                if (requestMethods.isEmpty()) {
                    putAllRequestMethod(newInstance, method, url);
                    continue;
                }

                for (RequestMethod requestMethod : requestMethods) {
                    putHandler(newInstance, method, new HandlerKey(url, requestMethod));
                }
            }
        }
    }

    private void putAllRequestMethod(Object instance, Method method, String url) {
        for (RequestMethod value : RequestMethod.values()) {
            HandlerKey handlerKey = new HandlerKey(url, value);
//            checkDuplicatedHandlerKey(handlerKey);
            putHandler(instance, method, handlerKey);
        }
    }

    private void putHandler(Object instance, Method method, HandlerKey handlerKey) {
        checkDuplicatedHandlerKey(handlerKey);
        handlerExecutions.put(handlerKey,
                (request, response) ->
                        method.invoke(instance,
                                HandlerMethodArgumentResolverManager.values(method, request, response))
        );
    }

    private void checkDuplicatedHandlerKey(HandlerKey handlerKey) {
        if (handlerExecutions.containsKey(handlerKey)) {
            throw new MappingException();
        }
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String path = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.of(request.getMethod());
        return handlerExecutions.get(
                handlerExecutions.keySet().stream()
                        .filter(key -> key.matches(path, requestMethod))
                        .findFirst()
                        .orElseThrow(NotMatchHandlerKeyException::new));
    }
}
