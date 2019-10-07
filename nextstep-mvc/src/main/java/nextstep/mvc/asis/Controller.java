package nextstep.mvc.asis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    String handle(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
