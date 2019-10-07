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

    private final Set<Class<?>> controllers;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(final Set<Class<?>> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void initialize() {
        for (final Class clazz : controllers) {
            List<Method> methods = generateMethods(clazz);
            methods.forEach(method -> addHandlerExecutions(clazz, method));
        }
        handlerExecutions.forEach((key, value) -> logger.debug("handlerExecutions key : {}, value : {}", key, value));
    }

    private List<Method> generateMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .filter(method -> isNotBlank(method.getAnnotation(RequestMapping.class)))
                .collect(Collectors.toList());
    }

    private boolean isNotBlank(RequestMapping requestMapping) {
        final String trimMethod = requestMapping.value().trim();
        return StringUtils.isNotBlank(trimMethod);
    }

    private void addHandlerExecutions(Class clazz, Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String url = requestMapping.value();
        RequestMethod[] requestMethods = requestMapping.method();

        if (requestMethods.length == 0) {
            requestMethods = RequestMethod.values();
        }
        Arrays.stream(requestMethods)
                .forEach(m -> handlerExecutions.put(new HandlerKey(url, m), new HandlerExecution(clazz, method)));
    }

    @Override
    @Nullable
    public HandlerExecution getHandler(HttpServletRequest request) {
        final String uri = request.getRequestURI();
        final RequestMethod method = RequestMethod.valueOf(request.getMethod());
        final HandlerKey key = new HandlerKey(uri, method);
        return handlerExecutions.get(key);
    }
}
