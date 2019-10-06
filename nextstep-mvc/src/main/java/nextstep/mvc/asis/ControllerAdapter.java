package nextstep.mvc.asis;

import nextstep.mvc.tobe.handlermapping.annotationmapping.HandlerExecution;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdapter implements HandlerExecution {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private final Controller controller;

    public ControllerAdapter(final Controller controller) {
        this.controller = controller;
    }

    @Override
    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        String viewName = controller.execute(request, response);
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            viewName = viewName.substring(DEFAULT_REDIRECT_PREFIX.length());
            return new ModelAndView(new RedirectView(viewName));
        }

        return new ModelAndView(new JspView(viewName));
    }
}
