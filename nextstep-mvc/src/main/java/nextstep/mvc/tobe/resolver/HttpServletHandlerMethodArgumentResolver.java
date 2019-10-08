package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return methodParameter.isSameType(HttpServletRequest.class)
                || methodParameter.isSameType(HttpServletResponse.class);
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        if (methodParameter.isSameType(HttpServletRequest.class)) {
            return webRequest.getRequest();
        }
        return webRequest.getResponse();
    }
}
