package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ControllerHandlerAdapter.class);

    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String forwardView = ((Controller) handler).execute(request, response);
        logger.debug("Controller execute result : {}", forwardView);

        return new ModelAndView(forwardView);
    }
}
