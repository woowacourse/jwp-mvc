package nextstep.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize() throws ServletException;

    Object getHandler(HttpServletRequest request);
}
