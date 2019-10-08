package nextstep;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String viewName = ((Controller) handler).execute(request, response);
        View view = ViewResolver.resolve(viewName);
        return new ModelAndView(view);
    }
}
