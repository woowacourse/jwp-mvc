package nextstep.mvc.tobe.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.exception.ManualMappingFailedException;
import slipp.ManualHandlerMapping;

public class ManualHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean isSupports(HandlerMapping handlerMapping) {
        return handlerMapping.getClass().getName().equals(ManualHandlerMapping.class.getName());
    }

    public ModelAndView handle(HandlerMapping manualHandlerMapping, HttpServletRequest req, HttpServletResponse resp) {
        try {
            Controller controller = (Controller) manualHandlerMapping.getHandler(req);
            String viewName = controller.execute(req, resp);
            return new ModelAndView(new JspView(viewName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ManualMappingFailedException();
    }
}
