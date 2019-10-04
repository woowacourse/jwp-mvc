package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        InstancePool.initControllerPoll(basePackage);

        InstancePool.controllerInstancePoolKeySet().stream()
                .flatMap(controller -> Arrays.stream(controller.getMethods()))
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(this::putHandlerExecution);

        log.info("Initialized Request Mapping!");
        handlerExecutions.keySet().forEach(handlerKey ->
                log.info("HandlerKey : {}, HandlerExecution : {}", handlerKey, handlerExecutions.get(handlerKey)));
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.resolve(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }

    private void putHandlerExecution(Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        annotation.method().getMethods().forEach(requestMethod -> {
            HandlerKey handlerKeys = new HandlerKey(annotation.value(), requestMethod);
            handlerExecutions.put(handlerKeys, new HandlerExecution(method));
        });
    }
}
