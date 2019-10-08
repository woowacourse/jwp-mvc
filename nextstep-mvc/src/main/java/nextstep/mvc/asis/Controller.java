package nextstep.mvc.asis;

import nextstep.mvc.tobe.HandlerResolver;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller extends HandlerResolver {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    @Override
    default ModelAndView resolve(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String viewName = execute(req, resp);
        JspView jspView = new JspView(viewName);
        return new ModelAndView(jspView);
    }
}
