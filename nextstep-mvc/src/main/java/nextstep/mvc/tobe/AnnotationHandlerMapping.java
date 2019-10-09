package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        controllers.forEach(this::fillHandlerExecutions);
    }

    private void fillHandlerExecutions(Class<?> aClass) {
        Arrays.stream(aClass.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(this::fillHandlerExecution);
    }

    private void fillHandlerExecution(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMapping.method()), new HandlerExecution(method));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(createHandlerKey(request));
    }

    private HandlerKey createHandlerKey(HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        return new HandlerKey(url, RequestMethod.valueOf(method));
    }
}
