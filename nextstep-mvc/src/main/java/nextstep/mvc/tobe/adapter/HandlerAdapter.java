package nextstep.mvc.tobe.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.ModelAndView;

public interface HandlerAdapter {
    ModelAndView handle(HandlerMapping handlerMapping, HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse);

    boolean isSupports(HandlerMapping handlerMapping);
}
