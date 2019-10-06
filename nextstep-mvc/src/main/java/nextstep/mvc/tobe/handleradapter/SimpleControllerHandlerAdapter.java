package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.view.ModelAndView;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(RequestContext requestContext, Object handler) throws Exception {
        Controller controller = (Controller) handler;
        return new ModelAndView(controller.execute(requestContext.getHttpServletRequest(), requestContext.getHttpServletResponse()));
    }
}
