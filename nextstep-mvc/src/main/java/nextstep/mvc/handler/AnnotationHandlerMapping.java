package nextstep.mvc.handler;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        HandlerScanner handlerScanner = new HandlerScanner(reflections);
        handlerExecutions = handlerScanner.getHandlerExecutions();
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
