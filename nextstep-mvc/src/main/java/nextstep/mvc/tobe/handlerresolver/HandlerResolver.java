package nextstep.mvc.tobe.handlerresolver;

import nextstep.mvc.tobe.handler.HandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerResolver {
    public void initialize();

    public HandlerExecution getHandler(HttpServletRequest req);

    public boolean support(HttpServletRequest req, HttpServletResponse resp);
}
