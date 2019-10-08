package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebRequest {
    HttpServletRequest getRequest();

    HttpServletResponse getResponse();

    String getRequestURI();
}
