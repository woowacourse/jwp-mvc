package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.WebRequest;

public interface HandlerMethodArgumentResolver {
    boolean supports(final MethodParameter methodParameter);

    Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter);
}
