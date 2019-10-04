package slipp;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.*;

import javax.servlet.http.HttpServletRequest;

public class ManualHandlerMappingAdapter implements HandlerMapping {
    private LegacyHandlerMapping handlerMapping;

    public ManualHandlerMappingAdapter(LegacyHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void initialize() {
        this.handlerMapping.initialize();
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        Controller handler = this.handlerMapping.getHandler(request.getRequestURI());

        if(handler == null) {
            return null;
        }

        return (req, resp) -> new ModelAndView(new JspView(handler.execute(req, resp)));
    }
}
