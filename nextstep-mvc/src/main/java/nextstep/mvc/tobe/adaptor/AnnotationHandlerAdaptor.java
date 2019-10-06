package nextstep.mvc.tobe.adaptor;

import nextstep.mvc.HandlerAdaptor;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdaptor implements HandlerAdaptor {

    @Override
    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        return ((HandlerExecution) handler).handle(request, response);
    }

    @Override
    public boolean isSupport(final Object handler) {
        return handler instanceof HandlerExecution;
    }
}
