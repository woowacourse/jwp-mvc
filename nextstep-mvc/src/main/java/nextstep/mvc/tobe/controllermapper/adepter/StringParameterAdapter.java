package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringParameterAdapter implements ParameterAdapter {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.checkType(String.class);
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) throws Exception {
        return request.getParameter(methodParameter.getParameterName());
    }
}
