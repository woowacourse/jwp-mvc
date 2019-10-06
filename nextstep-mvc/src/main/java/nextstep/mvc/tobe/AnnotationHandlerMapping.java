package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllMethods;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private Object[] basePackage;
    private ControllerScan controllerScan = new ControllerScan();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        ControllerScan controllerScan = scanController();

        for (Class<?> controller : controllerScan.getKeys()) {
            putHandlerExecution(controller, scanMethod(controller));
        }
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        RequestMethod[] requestMethod = {RequestMethod.valueOf(request.getMethod())};

        return handlerExecutions.get(new HandlerKey(url, requestMethod));
    }

    private ControllerScan scanController() {
        try {
            controllerScan.initialize(basePackage);
        } catch (IllegalAccessException | InstantiationException e) {
            log.debug("Controller Scanner Error");
        }

        return controllerScan;
    }

    private Set<Method> scanMethod(Class<?> controller) {
        return getAllMethods(controller, ReflectionUtils.withAnnotation(RequestMapping.class));
    }

    private void putHandlerExecution(Class<?> controller, Set<Method> allMethods) {
        for (Method method : allMethods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            handlerExecutions.put(createHandlerKey(rm),
                    ((request, response) -> (ModelAndView) method.invoke(controllerScan.getController(controller), request, response)));
        }
    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }
}
