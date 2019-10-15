package nextstep.mvc.tobe.argumentresolver;

import nextstep.utils.JsonUtils;
import nextstep.web.annotation.RequestBody;
import nextstep.web.support.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter parameter) {
        return parameter.hasAnnotation(RequestBody.class);
    }

    @Override
    public Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return JsonUtils.createObject(request, parameter.getParameterType());
    }
}
