package nextstep.mvc.handleradapter;

import nextstep.mvc.exception.UnsupportedHandlerResultClassException;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean canHandle(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerExecution castHandler = (HandlerExecution) handler;
        Object result = castHandler.handle(request, response);

        if(result instanceof ModelAndView) {
            return (ModelAndView) result;
        }

        if(result instanceof String) {
            return handleString(result);
        }

        throw new UnsupportedHandlerResultClassException();
    }

    private ModelAndView handleString(Object result) {
        String viewName = (String) result;
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new ModelAndView(new RedirectView(viewName));
        }
        return new ModelAndView(new JspView(viewName));
    }
}
