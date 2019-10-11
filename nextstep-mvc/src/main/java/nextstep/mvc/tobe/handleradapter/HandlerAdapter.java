package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.argumentresolver.HandlerMethodArgumentResolver;
import nextstep.mvc.tobe.returnvaluehandler.HandlerMethodReturnValueHandler;
import nextstep.mvc.tobe.view.ModelAndView;

import java.util.List;

public interface HandlerAdapter {
    boolean supports(Object handler);

    boolean hasResolvers();

    ModelAndView handle(RequestContext requestContext, Object handler);

    void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers);

    void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers);
}
