package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Handler {
    ModelAndView run(HttpServletRequest req, HttpServletResponse res) throws Exception;
}