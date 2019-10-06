package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.view.ModelAndView;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(RequestContext requestContext, Object handler) throws Exception {
        HandlerExecution handlerExecution = (HandlerExecution) handler;
        Object returnValue = handlerExecution.execute(requestContext.getHttpServletRequest(), requestContext.getHttpServletResponse());

        if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue);
        }

        return (ModelAndView) returnValue;
    }
}
