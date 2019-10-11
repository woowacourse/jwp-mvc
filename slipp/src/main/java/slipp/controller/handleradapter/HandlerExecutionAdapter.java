package slipp.controller.handleradapter;

import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionAdapter implements HandlerAdapter {
    private HandlerExecutionAdapter() {
    }

    private static class SingletonHolder {
        private static final HandlerExecutionAdapter INSTANCE = new HandlerExecutionAdapter();
    }

    public static HandlerExecutionAdapter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerExecution handlerExecution = (HandlerExecution) handler;
        return handlerExecution.handle(request, response);
    }
}
