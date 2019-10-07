package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.scanner.ControllerScanner;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() throws ServletException {
        logger.info("Initialized Request Mapping!");
        Map<Class<?>, Object> controllerInstanceMap = scanController();
        for (Map.Entry<Class<?>, Object> controllerSet : controllerInstanceMap.entrySet()) {
            List<Method> methods = generateMethods(controllerSet.getKey());
            methods.forEach(method -> addHandlerExecutions(controllerSet.getValue(), method));
        }
        handlerExecutions.forEach((key, value) -> logger.debug("handlerExecutions key : {}, value : {}", key, value));
    }

    private Map<Class<?>, Object> scanController() throws ServletException {
        try {
            return ControllerScanner.scan(basePackage);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }

    private List<Method> generateMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
            .filter(method -> StringUtils.isNotBlank(method.getAnnotation(RequestMapping.class).value().trim()))
            .collect(Collectors.toList());
    }

    private void addHandlerExecutions(Object clazzInstance, Method method) {
        String url = method.getAnnotation(RequestMapping.class).value();
        RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();
        if (requestMethods.length == 0) {
            requestMethods = RequestMethod.values();
        }
        Arrays.stream(requestMethods).forEach(m -> {
            handlerExecutions.put(
                new HandlerKey(url, m),
                new HandlerExecution(clazzInstance, method));
        });
    }

    @Override
    public Handler getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
