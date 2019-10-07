package nextstep.mvc.tobe.handlermapping;

import nextstep.mvc.Handler;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExecution extends Handler {
    @Override
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
