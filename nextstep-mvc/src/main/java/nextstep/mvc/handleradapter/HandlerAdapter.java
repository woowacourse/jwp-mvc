package nextstep.mvc.handleradapter;

import nextstep.mvc.argumentresolver.ArgumentResolvers;
import nextstep.mvc.modelandview.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean canHandle(Object handler);

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

    void addArugmentResolvers(ArgumentResolvers argumentResolvers1);
}
