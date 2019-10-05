package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMethodArgumentResolver {
    Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter);
}
