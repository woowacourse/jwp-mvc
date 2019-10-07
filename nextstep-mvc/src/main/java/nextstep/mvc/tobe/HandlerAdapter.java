package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerAdapter {
    private final Object handler;

    public HandlerAdapter(Object handler) {
        this.handler = handler;
    }

    public Object run(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (handler instanceof Controller) {
            return ((Controller) handler).execute(request, response);
        } else if (handler instanceof HandlerExecution) {
            return ((HandlerExecution) handler).handle(request, response);
        } else {
            throw new Exception("Not Match Handler");
        }
    }
}
