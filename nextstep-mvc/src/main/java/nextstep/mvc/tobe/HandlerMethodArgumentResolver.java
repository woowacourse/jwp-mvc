package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMethodArgumentResolver {
    boolean supports(final MethodParameter methodParameter);

    Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter);
}
