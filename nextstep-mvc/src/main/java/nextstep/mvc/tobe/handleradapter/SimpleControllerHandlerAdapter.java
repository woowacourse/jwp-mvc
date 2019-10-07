package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean isSupport(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String viewName = ((Controller) handler).execute(request, response);

        ModelAndView modelAndView = new ModelAndView(viewName);

        return modelAndView;
    }
}
