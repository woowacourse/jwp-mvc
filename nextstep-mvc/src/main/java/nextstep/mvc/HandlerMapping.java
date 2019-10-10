package nextstep.mvc;

import nextstep.mvc.tobe.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    HandlerAdapter getHandler(HttpServletRequest request);
}
