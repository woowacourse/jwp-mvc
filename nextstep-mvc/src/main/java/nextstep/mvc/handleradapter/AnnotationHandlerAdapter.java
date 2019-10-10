package nextstep.mvc.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean canHandle(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerExecution castHandler = (HandlerExecution) handler;
        Object result = castHandler.handle(request, response);
        if (result instanceof ModelAndView) {
            return (ModelAndView) result;
        }
        String viewName = (String) result;
        return new ModelAndView(viewName);
    }
}
