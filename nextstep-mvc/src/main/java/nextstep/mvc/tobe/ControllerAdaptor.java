package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdaptor implements HandlerExecution {
    private final Controller controller;

    public ControllerAdaptor(Controller controller) {
        this.controller = controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView(new JSPView(controller.execute(request, response)));
    }
}
