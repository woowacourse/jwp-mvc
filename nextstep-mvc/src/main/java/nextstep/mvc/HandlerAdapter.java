package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean isSupported(Object handler);

    ModelAndView execute(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
