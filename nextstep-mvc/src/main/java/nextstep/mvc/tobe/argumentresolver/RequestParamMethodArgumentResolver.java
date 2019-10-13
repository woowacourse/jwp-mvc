package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.MethodParameter;
import nextstep.mvc.tobe.RequestContext;
import nextstep.web.annotation.RequestParam;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.isAnnotatedWith(RequestParam.class);
    }

    @Override
    public Object resolve(RequestContext requestContext, MethodParameter methodParameter) {
        HttpServletRequest request = requestContext.getHttpServletRequest();
        RequestParam requestParam = (RequestParam) methodParameter.getAnnotation(RequestParam.class);
        if (isParamMap(methodParameter, requestParam)) {
            return request.getParameterMap();
        }
        return request.getParameter(requestParam.value());
    }

    private boolean isParamMap(MethodParameter methodParameter, RequestParam requestParam) {
        return StringUtils.isBlank(requestParam.value()) && methodParameter.getType().isAssignableFrom(Map.class);
    }
}
