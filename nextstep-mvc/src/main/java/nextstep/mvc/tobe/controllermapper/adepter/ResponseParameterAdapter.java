package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseParameterAdapter implements ParameterAdapter {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.checkType(HttpServletResponse.class);
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) throws Exception {
        return response;
    }
}
