package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean canAdapt(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handleInternal(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HandlerExecution handlerExecution = (HandlerExecution)handler;
        Object result = handlerExecution.handle(req,resp);
        if(result instanceof String){
            return new ModelAndView((String)result);
        }
        return (ModelAndView)result;
    }
}

