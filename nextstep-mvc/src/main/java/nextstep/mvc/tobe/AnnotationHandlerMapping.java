package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
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

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Controller.class);
        clazz.stream()
                .flatMap(c -> Arrays.stream(c.getDeclaredMethods())
                        .filter(m -> m.isAnnotationPresent(RequestMapping.class)))
                .forEach(this::createHandlerKey);
    }

    private void createHandlerKey(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestMethod[] methods = requestMapping.method();
        String value = requestMapping.value();
        if (methods.length == 0) methods = RequestMethod.values();

        try {
            Object object = method.getDeclaringClass().newInstance();
            HandlerExecution handlerExecution = new HandlerExecution(method, object);
            Arrays.stream(methods)
                    .map(m -> new HandlerKey(value, m))
                    .forEach(key -> handlerExecutions.put(key, handlerExecution));
        } catch (Exception e) {
            logger.info("Fail to create instance", e);
        }

    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
