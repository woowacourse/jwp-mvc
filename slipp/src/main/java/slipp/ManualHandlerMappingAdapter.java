package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.NoSuchControllerClassException;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManualHandlerMappingAdapter implements HandlerMapping {
    private ManualHandlerMapping handlerMapping;

    public ManualHandlerMappingAdapter(ManualHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void initialize() {
        this.handlerMapping.initialize();
    }

    @Override
    public ModelAndView getHandler(HttpServletRequest request, HttpServletResponse response) {
        try {
            String viewName = this.handlerMapping.getHandler(request.getRequestURI()).execute(request, response);
            return new ModelAndView(new JspView(viewName));
        } catch (Exception e) {
            throw new NoSuchControllerClassException();
        }
    }
}
