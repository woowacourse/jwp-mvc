package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.argumentresolver.HandlerMethodArgumentResolver;
import nextstep.mvc.tobe.returnvaluehandler.HandlerMethodReturnValueHandler;
import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SimpleControllerHandlerAdapter implements HandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleControllerHandlerAdapter.class);

    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public boolean hasResolvers() {
        return false;
    }

    @Override
    public ModelAndView handle(RequestContext requestContext, Object handler) {
        Controller controller = (Controller) handler;
        try {
            return new ModelAndView(controller.execute(requestContext.getHttpServletRequest(), requestContext.getHttpServletResponse()));
        } catch (Exception e) {
            logger.error("Http Request Exception : ", e);
            throw new HandlerExecutionFailedException();
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        throw new UnsupportedInstanceMethodException();
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        throw new UnsupportedInstanceMethodException();
    }
}
