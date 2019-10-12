package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object result = ((HandlerExecution) handler).handle(request, response);

        if (result.getClass().equals(String.class)) {
            return new ModelAndView(result.toString());
        }

        return (ModelAndView) result;
    }
}
