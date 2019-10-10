package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.exception.ControllerExecutionFailException;
import nextstep.mvc.tobe.view.EmptyView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class ControllerAdapter implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private Controller controller;

    public ControllerAdapter(Controller controller) {
        this.controller = controller;
    }

    public static ControllerAdapter of(Controller controller) {
        if (controller == null) {
            return null;
        }
        return new ControllerAdapter(controller);
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        String viewName;

        try {
            viewName = controller.execute(request, response);
            return viewName == null ? new ModelAndView(new EmptyView())
                    : createModelAndViewOf(viewName);
        } catch (Exception e) {
            throw new ControllerExecutionFailException();
        }
    }

    private ModelAndView createModelAndViewOf(String viewName) {
        return viewName.startsWith(DEFAULT_REDIRECT_PREFIX)
                ? new ModelAndView(new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length())))
                : new ModelAndView(new JspView(viewName));
    }
}
