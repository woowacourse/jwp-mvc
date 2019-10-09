package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class RequestHandler implements Handler {
    private final HandlerExecution handlerExecution;

    RequestHandler(HandlerExecution handlerExecution) {
        this.handlerExecution = handlerExecution;
    }

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return handlerExecution.execute(request, response);
    }
}
