package nextstep.mvc.tobe.handlerAdapter;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.argumentResolver.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

public interface HandlerAdapter {
    boolean canAdapt(Object handler);
    void setResolver(Set<HandlerMethodArgumentResolver> resolvers);
    ModelAndView handleInternal(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
