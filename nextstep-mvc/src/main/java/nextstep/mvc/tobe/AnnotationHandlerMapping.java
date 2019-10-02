package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        for (final Class clazz : controllers) {
            List<Method> methods = generateMethods(clazz);
            methods.forEach(method -> addHandlerExecutions(clazz, method));
        }
        handlerExecutions.entrySet().forEach(entry -> logger.debug("handlerExecutions key : {}, value : {}", entry.getKey(), entry.getValue()));
    }

    private List<Method> generateMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
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

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
