package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.exception.KeyNotFoundException;
import nextstep.mvc.exception.MappingException;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        controllerScanner.getControllers()
                .forEach(clazz -> ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class))
                        .forEach(classMethod -> {
                            RequestMapping rm = classMethod.getAnnotation(RequestMapping.class);
                            handlerExecutions.put(createHandlerKey(rm), createHandlerExecution(clazz, classMethod));
                        }));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod()));
        if (!handlerExecutions.containsKey(handlerKey)) {
            throw new KeyNotFoundException();
        }
        return handlerExecutions.get(handlerKey);
    }

    private RequestMethod[] isRequestMethodEmpty(RequestMethod[] requestMethod) {
        if (requestMethod.length == 0) {
            requestMethod = RequestMethod.values();
        }
        return requestMethod;
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        HandlerKey handlerkey = new HandlerKey(rm.value(), isRequestMethodEmpty(rm.method())[0]);
        if (handlerExecutions.containsKey(handlerkey)) {
            throw new MappingException();
        }
        return handlerkey;
    }

    private HandlerExecution createHandlerExecution(Class<?> clazz, Method method) {
        return new HandlerExecution(clazz, method);
    }
}
