package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean support(Object handler);

    ModelAndView handle(HttpServletRequest req, HttpServletResponse resq, Object handler) throws Exception;
}
