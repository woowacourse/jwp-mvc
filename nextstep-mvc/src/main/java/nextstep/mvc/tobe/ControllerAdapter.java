package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdapter implements HandlerAdapter {
    @Override
    public boolean isHandle(Object handler) {
        return Controller.class.isAssignableFrom(handler.getClass());
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String viewName = ((Controller) handler).execute(request, response);
        return new ModelAndView(viewName);
    }
}
