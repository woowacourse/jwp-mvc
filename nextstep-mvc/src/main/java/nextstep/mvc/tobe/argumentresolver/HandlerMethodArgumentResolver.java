package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;

public interface HandlerMethodArgumentResolver {
    boolean supports(MethodParameter methodParameter);

    Object resolve(RequestContext requestContext, MethodParameter methodParameter);
}
