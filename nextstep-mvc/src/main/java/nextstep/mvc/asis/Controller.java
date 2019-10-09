package nextstep.mvc.asis;

import nextstep.mvc.tobe.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Controller extends Handler {
    String handle(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}