package nextstep.mvc.tobe.handlermapping;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        controllers.forEach(this::handlerMappingForMethodsOf);
    }

    private void handlerMappingForMethodsOf(Class<?> controllerClass, Object controllerInstance) {
        Set<Method> annotatedMethods = getAllMethods(controllerClass, withAnnotation(RequestMapping.class));
        annotatedMethods.forEach(method -> handlerMapping(method, controllerInstance));
    }

    private void handlerMapping(Method method, Object controller) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        Arrays.stream(requestMapping.method())
                .forEach(requestMethod -> handlerExecutions.put(createHandlerKey(requestMapping.value(), requestMethod),
                (request, response) -> (ModelAndView) method.invoke(controller, request, response)));
    }

    private HandlerKey createHandlerKey(String url, RequestMethod requestMethod) {
        return new HandlerKey(url, requestMethod);
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
