package nextstep.mvc.tobe.adapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.WebRequest;

public class SimpleControllerAdapter implements HandlerAdapter {

    @Override
    public ModelAndView handle(final WebRequest webRequest, final Object handler) throws Exception {
        final Controller controller = (Controller) handler;
        final String viewName = String.valueOf((controller.execute(webRequest.getRequest(), webRequest.getResponse())));
        return new ModelAndView(viewName);
    }

    @Override
    public boolean supports(final Object handler) {
        return handler instanceof Controller;
    }
}
