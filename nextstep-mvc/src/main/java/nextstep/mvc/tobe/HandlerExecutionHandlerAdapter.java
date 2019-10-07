package nextstep.mvc.tobe;

import nextstep.mvc.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {

    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp, HandlerMapping handler) throws Exception {
        AnnotationHandlerMapping annotationHandlerMapping = (AnnotationHandlerMapping) handler;
        HandlerExecution handlerExecution = annotationHandlerMapping.getHandler(req);
        return handlerExecution.execute(req, resp);
    }
}
