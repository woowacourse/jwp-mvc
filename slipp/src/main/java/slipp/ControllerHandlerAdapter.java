package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {
    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp, HandlerMapping handler) throws Exception {
        ManualHandlerMapping manualHandlerMapping = (ManualHandlerMapping) handler;
        Controller controller = manualHandlerMapping.getHandler(req);
        return controller.execute(req, resp);
    }
}
