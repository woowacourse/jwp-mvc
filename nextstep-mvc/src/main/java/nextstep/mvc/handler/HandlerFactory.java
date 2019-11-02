package nextstep.mvc.handler;

import javax.servlet.http.HttpServletRequest;

public interface HandlerFactory {
    Handler create(HttpServletRequest request);
}
