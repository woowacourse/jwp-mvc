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
                .forEach(this::getRequestMappingMethod);
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod()));
        if (!handlerExecutions.containsKey(handlerKey)) {
            throw new KeyNotFoundException();
        }
        return handlerExecutions.get(handlerKey);
    }

    private HandlerKey createHandlerKey(String value, RequestMethod method) {
        HandlerKey handlerkey = new HandlerKey(value, method);
        if (handlerExecutions.containsKey(handlerkey)) {
            throw new MappingException();
        }
        return handlerkey;
    }

    private HandlerExecution createHandlerExecution(Class<?> clazz, Method method) {
        return new HandlerExecution(clazz, method);
    }

    private void getRequestMappingMethod(Class<?> clazz) {
        ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class))
                .forEach(classMethod -> addHandlerExecution(clazz, classMethod));
    }

    private void addHandlerExecution(Class<?> clazz, Method classMethod) {
        RequestMapping rm = classMethod.getAnnotation(RequestMapping.class);
        String value = rm.value();
        RequestMethod[] methods = getMethod(rm.method());

        for (RequestMethod method : methods) {
            handlerExecutions.put(createHandlerKey(value, method), createHandlerExecution(clazz, classMethod));
        }
    }

    private RequestMethod[] getMethod(RequestMethod[] method) {
        if (method.length == 0) {
            return RequestMethod.values();
        }

        return method;
    }
}
