package slipp;

import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.mvc.tobe.RedirectView.DEFAULT_REDIRECT_PREFIX;

public class ManualHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean isSupported(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String viewName = ((Controller) handler).execute(request, response);

        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new ModelAndView(new RedirectView(viewName));
        }

        return new ModelAndView(new JspView(viewName));
    }
}
