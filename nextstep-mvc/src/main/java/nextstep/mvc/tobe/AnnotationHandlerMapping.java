package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.NoSuchControllerClassException;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllMethods;

public class AnnotationHandlerMapping implements HandlerMapping {
    private final Object[] basePackage;

    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private final ControllerScanner controllerScanner;

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
        try {
            this.controllerScanner = new ControllerScanner(basePackage);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new NoSuchControllerClassException();
        }
    }

    @Override
    public void initialize() {
        for (Class<?> aClass : controllerScanner.getKeys()) {
            Set<Method> allMethods = getAllMethods(aClass, ReflectionUtils.withAnnotation(RequestMapping.class));
            for (Method method : allMethods) {
                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                handlerExecutions.put(createHandlerKey(rm),
                        ((request, response) -> (ModelAndView) method.invoke(aClass.newInstance(), request, response)));
            }
        }
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        RequestMethod[] requestMethod = {RequestMethod.valueOf(request.getMethod())};

        return handlerExecutions.get(new HandlerKey(url, requestMethod));
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }
}
