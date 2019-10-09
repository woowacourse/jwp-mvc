package nextstep.mvc.asis;

import nextstep.mvc.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller extends Handler {
    @Override
    String handle(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
