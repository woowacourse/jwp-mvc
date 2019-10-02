package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;

import javax.servlet.http.HttpServletRequest;

public interface ModelAndViewHandlerMapping extends HandlerMapping {
    HandlerExecution getHandler(HttpServletRequest req);
}
