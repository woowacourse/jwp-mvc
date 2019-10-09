package nextstep.mvc.asis;

import nextstep.mvc.Handler;
import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean support(Handler handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(Handler handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = ((Controller) handler).handle(request, response);

        return ModelAndView.of(viewName);
    }
}
