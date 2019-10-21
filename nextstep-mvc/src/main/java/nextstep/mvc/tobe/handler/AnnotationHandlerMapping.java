package nextstep.mvc.tobe.handler;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.scanner.ControllerScanner;
import nextstep.mvc.tobe.scanner.RequestMappingScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        logger.info("annotation mapping initialized!");
        Set<Class<?>> controllerAnnotatedClazz = ControllerScanner.scan(basePackage);
        handlerExecutions = RequestMappingScanner.scan(controllerAnnotatedClazz);

        handlerExecutions.keySet().forEach(key -> {
            logger.info("Path : {}, Controller : {}", key, handlerExecutions.get(key).getClass());
        });
    }

    @Override
    public Object getHandler(HttpServletRequest req) {
        return handlerExecutions.get(new HandlerKey(req));
    }

    public boolean support(HttpServletRequest req, HttpServletResponse resp) {
        return handlerExecutions.get(new HandlerKey(req)) != null;
    }
}
