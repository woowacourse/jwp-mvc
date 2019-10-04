package nextstep.mvc.asis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller extends Execution {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
