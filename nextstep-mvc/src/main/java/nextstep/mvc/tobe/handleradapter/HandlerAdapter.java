package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.argumentresolver.HandlerMethodArgumentResolver;
import nextstep.mvc.tobe.view.ModelAndView;

import java.util.List;

public interface HandlerAdapter {
    boolean supports(Object handler);

    boolean hasArgumentResolvers();

    ModelAndView handle(RequestContext requestContext, Object handler) throws Exception;

    void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers);
}
