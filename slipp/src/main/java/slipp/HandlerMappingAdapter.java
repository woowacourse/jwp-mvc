package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class HandlerMappingAdapter implements HandlerMapping {
    private LegacyHandlerMapping handlerMapping;

    HandlerMappingAdapter(LegacyHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void initialize() {
        this.handlerMapping.initialize();
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        Controller handler = this.handlerMapping.getHandler(request.getRequestURI());
        if (handler == null) {
            return null;
        }
        return (req, resp) -> new ModelAndView(new JspView(handler.execute(req, resp)));
    }
}
