package nextstep.mvc;

import nextstep.mvc.tobe.ServletRequestHandler;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    ServletRequestHandler getHandler(HttpServletRequest request);
}
