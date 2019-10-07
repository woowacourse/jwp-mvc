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
    private static Map<Class, ViewResolver> viewResolvers = Maps.newHashMap();

    static {
        viewResolvers.put(String.class, new JspViewResolver());
    }

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

    private void fillHandlerExecutions(Class clazz) {
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> fillHandlerExecution(method, clazz));
    }

    private void fillHandlerExecution(Method method, Class clazz) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMapping.method()), new HandlerExecution(method, clazz, viewResolvers.get(method.getReturnType())));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest req) {
        return handlerExecutions.get(createHandlerKey(req));
    }

    private HandlerKey createHandlerKey(HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        return new HandlerKey(url, RequestMethod.valueOf(method));
    }

    @Override
    public boolean supports(HttpServletRequest req) {
        return getHandler(req) != null;
    }
}
