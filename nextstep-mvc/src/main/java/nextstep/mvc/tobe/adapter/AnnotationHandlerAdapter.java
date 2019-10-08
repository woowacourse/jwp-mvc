package nextstep.mvc.tobe.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.exception.AnnotationMappingFailedException;
import nextstep.mvc.tobe.exception.ManualMappingFailedException;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean isSupports(HandlerMapping handlerMapping) {
        return handlerMapping.getClass().getName().equals(AnnotationHandlerMapping.class.getName());
    }

    @Override
    public ModelAndView handle(HandlerMapping annotationHandlerMapping, HttpServletRequest req,
                               HttpServletResponse resp) {
        try {
            HandlerExecution handlerExecution = (HandlerExecution) annotationHandlerMapping.getHandler(req);
            return handlerExecution.handle(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new AnnotationMappingFailedException();
    }
}
