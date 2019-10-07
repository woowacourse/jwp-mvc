package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.web.annotation.RequestParam;

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.isAnnotatedWith(RequestParam.class);
    }

    @Override
    public Object resolve(RequestContext requestContext, MethodParameter methodParameter) {
        RequestParam requestParam = (RequestParam) methodParameter.getAnnotation(RequestParam.class);
        return requestContext.getHttpServletRequest().getParameter(requestParam.value());
    }
}
