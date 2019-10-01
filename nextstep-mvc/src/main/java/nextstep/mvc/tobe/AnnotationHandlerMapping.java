package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.support.MediaType;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Handler;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        controllers.stream()
                .flatMap(controller -> Arrays.stream(controller.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    HandlerKey handlerKey = new HandlerKey(annotation.value(), annotation.method());
                    handlerExecutions.put(handlerKey, new HandlerExecution(method));
                });
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.resolve(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
