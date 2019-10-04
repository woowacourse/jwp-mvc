package nextstep.mvc.tobe.handlermapping;

import nextstep.mvc.tobe.handlermapping.annotationmapping.HandlerExecution;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    HandlerExecution getHandler(HttpServletRequest request);
}
