package nextstep.mvc.tobe.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpSessionArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return HttpSession.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        return request.getSession();
    }
}
