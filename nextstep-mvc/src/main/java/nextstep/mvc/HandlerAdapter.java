package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean isSupported(HttpServletRequest request);

    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
