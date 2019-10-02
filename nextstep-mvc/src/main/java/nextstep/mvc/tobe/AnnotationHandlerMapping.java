package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        for (Object basePackage : basePackage) {
            Reflections reflections = new Reflections(basePackage);
            reflections.getTypesAnnotatedWith(Controller.class).forEach(controllerClazz -> {
                log.debug("controllerClazz : {}", controllerClazz);

                Arrays.stream(controllerClazz.getDeclaredMethods()).forEach(method -> {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

                    RequestMethod[] requestMethods = requestMapping.method();
                    if (requestMethods.length == 0) {
                        requestMethods = RequestMethod.values();
                    }

                    Arrays.stream(requestMethods).forEach(requestMethod -> {
                        log.debug("requestMapping : {}", new HandlerKey(requestMapping.value(), requestMethod));
                        handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMethod), new HandlerExecution(method, controllerClazz));
                    });
                });
            });
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        log.debug("path: {}", request.getRequestURI());
        log.debug("path: {}",  RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
