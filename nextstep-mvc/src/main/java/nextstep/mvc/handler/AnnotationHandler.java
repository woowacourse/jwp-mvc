package nextstep.mvc.handler;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandler extends AbstractHandlerAdapter {

    public AnnotationHandler(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        return ((HandlerExecution) handler).handle(req, resp);
    }
}
