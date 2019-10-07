package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.RequestContextKey;

public class ServletMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return RequestContextKey.hasType(methodParameter.getType());
    }

    @Override
    public Object resolve(RequestContext requestContext, MethodParameter methodParameter) {
        return requestContext.getAttribute(methodParameter.getType().getName());
    }
}
