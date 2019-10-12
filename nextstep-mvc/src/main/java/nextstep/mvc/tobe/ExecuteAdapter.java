package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecuteAdapter implements Adapter {
    @Override
    public boolean isSupport(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return (ModelAndView) ((Controller) handler).execute(req, resp);
    }
}
