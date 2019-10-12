package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean isSupport(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object view = ((HandlerExecution) handler).execute(request, response);
        if (view instanceof ModelAndView) {
            return (ModelAndView) view;
        }
        return new ModelAndView(view);
    }
}
