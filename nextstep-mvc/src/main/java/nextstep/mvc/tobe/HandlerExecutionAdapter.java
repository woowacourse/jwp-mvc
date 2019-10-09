package nextstep.mvc.tobe;

import nextstep.mvc.Handler;
import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.tobe.handlermapping.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionAdapter implements HandlerAdapter {

    @Override
    public boolean support(Handler handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(Handler handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ;
        return ((HandlerExecution) handler).handle(request, response);
    }
}
