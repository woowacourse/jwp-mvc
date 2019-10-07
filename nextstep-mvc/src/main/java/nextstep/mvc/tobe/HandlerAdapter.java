package nextstep.mvc.tobe;

import nextstep.mvc.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    Object handle(HttpServletRequest req, HttpServletResponse resp, HandlerMapping handler) throws Exception;
}
