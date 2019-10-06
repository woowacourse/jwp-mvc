package nextstep.mvc.tobe.adaptor;

import nextstep.mvc.HandlerAdaptor;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManualHandlerAdaptor implements HandlerAdaptor {
    @Override
    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String viewName = ((Controller) handler).execute(request, response);
        return new ModelAndView(new JspView(viewName));
    }

    @Override
    public boolean isSupport(final Object handler) {
        return handler instanceof Controller;
    }
}
