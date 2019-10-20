package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotatedHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof Handler;
    }

    @Override
    public ModelAndView run(Object handler, HttpServletRequest req, HttpServletResponse res) throws Exception {
        return ((Handler) handler).run(req, res);
    }
}