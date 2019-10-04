package nextstep.mvc.asis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    Object execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
