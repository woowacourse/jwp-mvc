package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMapping {
    void initialize();

    ModelAndView getHandler(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
