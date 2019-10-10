package nextstep.mvc.asis;

import nextstep.mvc.tobe.Handler;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller extends Handler {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    @Override
    default ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String redirect_prefix = "redirect:";
        String view = execute(request, response);
        if (view.startsWith(redirect_prefix)) {
            return new ModelAndView(new RedirectView(view.substring(redirect_prefix.length())));
        }
        return new ModelAndView(new JspView(view));
    }
}
