package nextstep.mvc.tobe.argumentresolver;

import nextstep.web.annotation.RequestParam;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestParamArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(RequestParam.class) ||
                (methodParameter.hasNoAnnotation() && PrimitiveParser.canParse(methodParameter.getType()));
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        String parameterName = getParameterName(methodParameter);
        Class<?> paramType = methodParameter.getType();
        return PrimitiveParser.parse(request.getParameter(parameterName), paramType);
    }

    private String getParameterName(MethodParameter methodParameter) {
        RequestParam requestParam = methodParameter.getDeclaredAnnotation(RequestParam.class);
        if (requestParam == null || StringUtils.isEmpty(requestParam.name())) {
            return methodParameter.getParameterName();
        }
        return requestParam.name();
    }
}
