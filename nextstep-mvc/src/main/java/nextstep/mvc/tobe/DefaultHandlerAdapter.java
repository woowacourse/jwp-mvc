package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultHandlerAdapter implements HandlerAdapter {
    @Override
    public ModelAndView handle(final HttpServletRequest req, final HttpServletResponse resp, final Handler handler) throws Exception {
        final Object result = handler.execute(req, resp);
        if (result instanceof String) {
            return new ModelAndView(String.valueOf(result));
        }
        return (ModelAndView) result;
    }

    @Override
    public boolean supports(final Handler handler) {
        return handler instanceof HandlerExecution || handler instanceof Controller;
    }
}