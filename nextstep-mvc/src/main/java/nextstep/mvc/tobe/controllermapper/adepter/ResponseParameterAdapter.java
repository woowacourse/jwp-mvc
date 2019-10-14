package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseParameterAdapter implements ParameterAdapter {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(HttpServletResponse.class);
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, String parameterName) throws Exception {
        return response;
    }
}
