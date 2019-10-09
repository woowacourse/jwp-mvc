package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(final Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        final String viewName = ((Controller) handler).execute(request, response);
        return new ModelAndView(processView(viewName));
    }

    private View processView(final String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
        }
        return new JspView(viewName);
    }
}
