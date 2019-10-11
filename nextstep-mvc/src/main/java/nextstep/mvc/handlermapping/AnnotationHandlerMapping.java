package nextstep.mvc.handlermapping;

import com.google.common.collect.Maps;
import nextstep.mvc.handlermapping.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackages;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public void initialize() {
        try {
            Reflections reflections = new Reflections(basePackages);
            checkClazz(reflections);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public boolean canHandle(HttpServletRequest request) {
        return handlerExecutions.containsKey(new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod())));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.of(request.getMethod())));
    }

    private void checkClazz(Reflections reflections) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Controller.class)) {
            checkMethods(clazz);
        }
    }

    private void checkMethods(Class<?> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (Method method : clazz.getDeclaredMethods()) {
            putHandlerExecution(method);
        }
    }

    private void putHandlerExecution(Method method) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            RequestMethod[] requestMethods = selectMethods(requestMapping);
            Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            Stream.of(requestMethods)
                    .forEach(requestMethod -> handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMethod)
                            , new HandlerExecution(method, instance)));
        }
    }

    private RequestMethod[] selectMethods(RequestMapping requestMapping) {
        if (requestMapping.method().length == 0) {
            return RequestMethod.values();
        }
        return requestMapping.method();
    }
}