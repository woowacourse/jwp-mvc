package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean canAdapt(Object handler);

    ModelAndView handleInternal(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
