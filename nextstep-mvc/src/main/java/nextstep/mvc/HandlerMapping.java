package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMapping {
    void initialize();

    HandlerExecution getHandler(HttpServletRequest request, HttpServletResponse response);
}
