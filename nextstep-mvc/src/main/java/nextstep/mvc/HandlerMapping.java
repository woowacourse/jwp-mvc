package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    HandlerExecution getHandler(HttpServletRequest request);

    boolean containsKey(HttpServletRequest request);
}
