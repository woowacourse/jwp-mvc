package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotatedWithController = reflections.getTypesAnnotatedWith(Controller.class);
        annotatedWithController.forEach(this::handlerMappingForMethodsOf);
    }

    private void handlerMappingForMethodsOf(Class<?> controller) {
        Arrays.stream(controller.getDeclaredMethods())
                .forEach(method -> {
                    Controller controllerMetaData = controller.getAnnotation(Controller.class);
                    handlerMapping(method, controllerMetaData);
                });
    }

    private void handlerMapping(Method method, Controller controller) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            Class<?> clazz = method.getDeclaringClass();
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

            Arrays.stream(requestMapping.method())
                    .forEach(requestMethod -> {
                        String path = controller.path() + requestMapping.value();
                        handlerExecutions.put(new HandlerKey(path, requestMethod),
                        (request, response) -> (ModelAndView) method.invoke(clazz.newInstance(), request, response));
                    });
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
