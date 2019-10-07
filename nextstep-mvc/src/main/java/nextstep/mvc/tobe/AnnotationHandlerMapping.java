package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.exception.MappingException;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                List<RequestMethod> requestMethod =
                        Arrays.asList(method.getAnnotation(RequestMapping.class).method());

                if (requestMethod.isEmpty()) {
                    putAllRequestMethod(newInstance, method, url);
                    return;
                }

                putHandler(newInstance, method, new HandlerKey(url, requestMethod.get(0)));
            }
        }
    }

    private void putAllRequestMethod(Object instance, Method method, String url) {
        for (RequestMethod value : RequestMethod.values()) {
            HandlerKey handlerKey = new HandlerKey(url, value);
            if (handlerExecutions.containsKey(handlerKey)) {
                throw new MappingException();
            }
            putHandler(instance, method, handlerKey);
        }
    }

    private void putHandler(Object instance, Method method, HandlerKey handlerKey) {
        handlerExecutions.put(handlerKey,
                (request, response) -> method.invoke(instance, request, response));
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
