package nextstep.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMapping {
    void initialize();

    boolean handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException;
}
