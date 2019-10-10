package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.utils.ComponentScanner;
import nextstep.utils.ValueExtractor;
import nextstep.utils.ValueTargets;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private static final ValueTargets requestMappingTargets = ValueTargets.from(new HashMap<>() {{
        put("value", String.class);
        put("method", RequestMethod[].class);
    }});

    private Object[] basePackages;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackages) {
        this.basePackages = basePackages;
    }

    public void initialize() {
        for (Object basePackage : basePackages) {
            if (!(basePackage instanceof String)) {
                log.error("not supported basePackage: {}", basePackage.getClass());
                return;
            }
            registerHandler((String) basePackage);
        }
    }

    private void registerHandler(String basePackagePrefix) {
        ComponentScanner componentScanner = ComponentScanner.fromBasePackagePrefix(basePackagePrefix);

        for (final Class<?> controllerClass : componentScanner.scanAnnotatedClasses(Controller.class)) {
            Arrays.asList(controllerClass.getDeclaredMethods()).stream()
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> {
                        HandlerKey handlerKey = makeHandlerKey(method);
                        HandlerExecution handlerExecution = makeHandlerExecution(method, controllerClass);

                        handlerExecutions.put(handlerKey, handlerExecution);
                    });
        }
    }

    private HandlerKey makeHandlerKey(Method method) {
        Annotation annotation = method.getAnnotation(RequestMapping.class);

        Map<String, Object> extracted = ValueExtractor.extractFromAnnotation(annotation, requestMappingTargets);
        String value = (String) extracted.get("value");
        RequestMethod[] methods = (RequestMethod[]) extracted.get("method");

        return new HandlerKey(value, (methods == null) ? null : methods[0]);
    }

    private HandlerExecution makeHandlerExecution(Method method, Class<?> controllerClass) {
        return new HandlerExecution() {
            public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
                return (ModelAndView) method.invoke(controllerClass.getDeclaredConstructor().newInstance(), request, response);
            }
        };
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        log.debug("URI: {}", request.getRequestURI());
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));

        if (handlerExecutions.containsKey(handlerKey)) {
            return handlerExecutions.get(handlerKey);
        }

        HandlerKey emptyMethodHandlerKey = new HandlerKey(request.getRequestURI(), null);
        return handlerExecutions.get(emptyMethodHandlerKey);
    }
}
