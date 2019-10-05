package nextstep.mvc.asis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Execution {
    Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
