package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {
    void resolve(HttpServletRequest req, HttpServletResponse resp, Object view) throws Exception;
}