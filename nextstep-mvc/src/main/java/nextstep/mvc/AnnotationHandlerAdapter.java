package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean isSupported(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return ((HandlerExecution) handler).execute(request, response);
    }
}
