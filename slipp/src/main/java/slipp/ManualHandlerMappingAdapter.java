package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;

public class ManualHandlerMappingAdapter implements HandlerMapping {
    private ManualHandlerMapping handlerMapping;

    ManualHandlerMappingAdapter(ManualHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void initialize() {
        this.handlerMapping.initialize();
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return this.handlerMapping.getHandler(request.getRequestURI());
    }
}
