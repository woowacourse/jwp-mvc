package nextstep.mvc;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean support(Handler handler);

    ModelAndView handle(Handler handler, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
