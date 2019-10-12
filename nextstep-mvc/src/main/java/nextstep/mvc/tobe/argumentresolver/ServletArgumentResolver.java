package nextstep.mvc.tobe.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return HttpServletRequest.class.equals(methodParameter.getParameterType()) ||
                HttpServletResponse.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        if (HttpServletRequest.class.equals(methodParameter.getParameterType())) {
            return request;
        }
        return response;
    }
}
