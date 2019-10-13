package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    boolean supports(HttpServletRequest request);

    HandlerExecution getHandler(HttpServletRequest request);
}
