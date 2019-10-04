package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    HandlerMapping annotationHandler;

    public AnnotationHandlerAdapter(HandlerMapping annotationHandler) {
        this.annotationHandler = annotationHandler;
    }

    @Override
    public boolean isSupported(HttpServletRequest req) {
        return Objects.nonNull(annotationHandler.getHandler(req));
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ((HandlerExecution) annotationHandler.getHandler(request)).handle(request, response);
    }
}
