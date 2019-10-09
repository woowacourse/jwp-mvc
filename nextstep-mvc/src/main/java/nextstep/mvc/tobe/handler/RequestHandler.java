package nextstep.mvc.tobe.handler;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHandler implements Handler {
    private final HandlerExecution handlerExecution;

    public RequestHandler(HandlerExecution handlerExecution) {
        this.handlerExecution = handlerExecution;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handlerExecution.execute(request, response);
    }
}
