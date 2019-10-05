package nextstep.mvc.tobe.core;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.scanner.ControllerScanner;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AnnotationHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        ControllerScanner scanner = new ControllerScanner(reflections);
        handlerExecutions = scanner.scan();
        for (Map.Entry<HandlerKey, HandlerExecution> entry : handlerExecutions.entrySet()) {
            logger.debug("Scanned {} mapping {}", entry.getKey(), entry.getValue());
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(HandlerKey.of(request));
    }
}
