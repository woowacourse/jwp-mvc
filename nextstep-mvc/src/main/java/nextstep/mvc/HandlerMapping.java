package nextstep.mvc;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    Object getHandler(HttpServletRequest request);
}
