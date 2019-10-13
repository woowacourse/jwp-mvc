package nextstep.mvc.handlermapping;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExecution {
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response);
}
