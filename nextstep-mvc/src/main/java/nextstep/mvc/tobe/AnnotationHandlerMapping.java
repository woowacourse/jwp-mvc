package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    public static final int DEFAULT_REQUEST_METHODS_LENGTH = 0;
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> clazz : classes) {
            filterMethods(clazz);
        }
    }

    private void filterMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            filterRequestMapping(method);
        }
    }

    private void filterRequestMapping(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            RequestMethod[] requestMethods = annotation.method();
            if (isDefaultRequestMethod(requestMethods)) {
                make(method, annotation, RequestMethod.values());
                return;
            }
            make(method, annotation, requestMethods);
        }
    }

    private boolean isDefaultRequestMethod(RequestMethod[] requestMethods) {
        return requestMethods.length == DEFAULT_REQUEST_METHODS_LENGTH;
    }

    // TODO: 메서드 이름 적절한 것으로 변경하기
    private void make(Method method, RequestMapping annotation, RequestMethod[] values) {
        for (RequestMethod value : values) {
            HandlerKey handlerKey = new HandlerKey(annotation.value(), value);
            handlerExecutions.put(handlerKey, new HandlerExecution(method));
        }
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(),
                RequestMethod.valueOf(request.getMethod().toUpperCase()));
        return handlerExecutions.get(handlerKey);
    }
}
