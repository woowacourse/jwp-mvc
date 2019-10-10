package nextstep.mvc;

import nextstep.mvc.tobe.Handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize() throws ServletException;

    Handler getHandler(HttpServletRequest request);
}
