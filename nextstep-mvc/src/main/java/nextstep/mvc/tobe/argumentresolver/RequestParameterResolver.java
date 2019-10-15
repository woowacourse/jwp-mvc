package nextstep.mvc.tobe.argumentresolver;

import nextstep.web.annotation.RequestParam;
import nextstep.web.support.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestParameterResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter parameter) {
        return parameter.hasAnnotation(RequestParam.class);
    }

    @Override
    public Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        String value = request.getParameter(parameter.getParameterName());
        Class<?> type = parameter.getParameterType();

        return TypeParser.parse(type, value);
    }
}
