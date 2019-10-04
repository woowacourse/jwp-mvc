package nextstep.mvc.tobe.handlermapping;

import nextstep.mvc.tobe.handlermapping.annotationmapping.HandlerExecution;

import javax.servlet.http.HttpServletRequest;

public interface ModelAndViewHandlerMapping extends HandlerMapping {
    HandlerExecution getHandler(HttpServletRequest req);
}
