package nextstep.mvc.handleradapter;

import nextstep.mvc.handlermapping.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionAdapter implements Handler {
    private final HandlerExecution handlerExecution;

    private HandlerExecutionAdapter(HandlerExecution handlerExecution) {
        this.handlerExecution = handlerExecution;
    }

    public static HandlerExecutionAdapter from(HandlerExecution handlerExecution) {
        return new HandlerExecutionAdapter(handlerExecution);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handlerExecution.handle(request, response);
    }
}
