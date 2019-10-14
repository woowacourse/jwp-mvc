package nextstep.mvc.tobe.argumentResolver;

import nextstep.mvc.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletArgumentResolver implements HandlerMethodArgumentResolver{

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return HttpServletRequest.class.equals(methodParameter.getType()) ||
                HttpServletResponse.class.equals(methodParameter.getType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        if(HttpServletResponse.class.equals(methodParameter.getType())){
            return response;
        }
        return request;
    }
}
