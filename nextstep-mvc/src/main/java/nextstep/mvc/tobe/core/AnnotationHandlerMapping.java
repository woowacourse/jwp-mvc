package nextstep.mvc.tobe.core;

import com.google.common.collect.Maps;
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

    @SuppressWarnings("unchecked")
    public void initialize() {
        ComponentScanner scanner = new ControllerScanner(basePackage);
        try {
            handlerExecutions = (Map<HandlerKey, HandlerExecution>) scanner.scan();
        } catch (ClassCastException e) {
            logger.error(e.getMessage());
            throw new FailToInitializeException(e.getMessage());
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(HandlerKey.of(request));
    }
}
