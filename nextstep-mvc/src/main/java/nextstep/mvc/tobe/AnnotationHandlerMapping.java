package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        // TODO: 2019-10-03 2중 for 리펙토링
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class controller : controllers) {
            List<Method> methods = Arrays.stream(controller.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .collect(Collectors.toList());
            for (Method method : methods) {
                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                try {
                    handlerExecutions.put(new HandlerKey(rm.value(), rm.method()[0]), new HandlerExecution(controller, method));
                } catch (ArrayIndexOutOfBoundsException e) {
                    handlerExecutions.put(new HandlerKey(rm.value(), RequestMethod.EMPTY), new HandlerExecution(controller, method));
                }
            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        HandlerExecution handlerExecution = handlerExecutions.get(new HandlerKey(uri, RequestMethod.valueOf(method)));
        if (handlerExecution != null) {
            return handlerExecution;
        }
        HandlerKey handlerKey = handlerExecutions.keySet().stream()
                .filter(keySet -> keySet.getUrl().equals(uri))
                .findFirst()
                .orElseThrow(NotFoundHandlerException::new);
        return handlerExecutions.get(handlerKey);
    }
}
