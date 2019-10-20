package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IntegerParameterAdapter implements ParameterAdapter {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.checkType(int.class);
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) throws Exception {
        return Integer.valueOf(request.getParameter(methodParameter.getParameterName()));
    }
}
