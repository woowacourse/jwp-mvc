package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMapping {
    void initialize();

    boolean isSupport(final HttpServletRequest request);

    ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception;
}
