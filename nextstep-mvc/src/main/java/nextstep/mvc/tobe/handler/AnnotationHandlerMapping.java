package nextstep.mvc.tobe.handler;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private Map<Class<?>, Object> instances = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Controller.class);
        clazz.stream()
                .peek(this::createInstance)
                .flatMap(c -> Arrays.stream(c.getDeclaredMethods())
                        .filter(m -> m.isAnnotationPresent(RequestMapping.class)))
                .forEach(this::createHandlerKey);
    }

    private void createInstance(Class<?> c) {
        try {
            instances.put(c, c.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            logger.error("error: {}", ExceptionUtils.getStackTrace(e));
        }
    }

    private void createHandlerKey(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] methods = requestMapping.method();
        if (methods.length == 0) {
            methods = RequestMethod.values();
        }
        String value = requestMapping.value();
        try {
            for (RequestMethod m : methods) {
                HandlerKey key = new HandlerKey(value, m);
                handlerExecutions.put(key, new HandlerExecution(method, instances.get(method.getDeclaringClass())));
            }
        } catch (Exception e) {
            logger.error("error: {}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
