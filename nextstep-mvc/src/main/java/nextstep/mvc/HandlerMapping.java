package nextstep.mvc;

import nextstep.mvc.asis.Execution;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    Execution getHandler(HttpServletRequest request);
}
