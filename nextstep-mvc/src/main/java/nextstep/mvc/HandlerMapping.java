package nextstep.mvc;

import nextstep.mvc.tobe.Handler;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    Handler getHandler(HttpServletRequest request);
}