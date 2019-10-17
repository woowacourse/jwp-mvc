package nextstep.mvc.tobe;

import nextstep.mvc.tobe.exception.UnsupportedHandlerExecutionReturnType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object result = ((HandlerExecution) handler).handle(request, response);

        if (result instanceof String) {
            return new ModelAndView((String) result);
        }

        if (result instanceof View) {
            return new ModelAndView((View) result);
        }

        if (result instanceof ModelAndView) {
            return (ModelAndView) result;
        }

        if (result != null) {
            return new ModelAndView(result);
        }

        return new ModelAndView();
    }
}
