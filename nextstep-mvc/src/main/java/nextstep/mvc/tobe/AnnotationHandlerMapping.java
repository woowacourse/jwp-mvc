package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        for (final Class clazz : controllers) {
            List<Method> methods = generateMethods(clazz);
            methods.forEach(method -> addHandlerExecutions(clazz, method));
        }
        handlerExecutions.forEach((key, value) -> logger.debug("handlerExecutions key : {}, value : {}", key, value));
    }

    private List<Method> generateMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
            .filter(method -> StringUtils.isNotBlank(method.getAnnotation(RequestMapping.class).value().trim()))
            .collect(Collectors.toList());
    }

    private void addHandlerExecutions(Class clazz, Method method) {
        String url = method.getAnnotation(RequestMapping.class).value();
        RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();
        if (requestMethods.length == 0) {
            requestMethods = RequestMethod.values();
        }
        Arrays.stream(requestMethods).forEach(m -> {
            handlerExecutions.put(
                new HandlerKey(url, m),
                new HandlerExecution(clazz, method));
        });
    }

    @Override
    @Nullable
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey key = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(key);
    }
}
