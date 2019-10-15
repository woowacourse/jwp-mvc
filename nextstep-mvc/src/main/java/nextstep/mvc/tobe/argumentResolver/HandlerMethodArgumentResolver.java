package nextstep.mvc.tobe.argumentResolver;

import nextstep.mvc.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter methodParameter);

    Object resolveArgument(MethodParameter methodParameter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
