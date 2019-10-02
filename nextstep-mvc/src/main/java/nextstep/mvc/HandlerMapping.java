package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    boolean canHandle(HttpServletRequest request);

    void initialize();

    HandlerExecution getHandler(HttpServletRequest request);
}
