package nextstep.mvc.tobe.argumentresolver;

import nextstep.web.support.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMethodArgumentResolver {
    boolean supports(MethodParameter parameter);

    Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response);
}
