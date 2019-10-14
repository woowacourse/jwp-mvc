package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IntegerParameterAdapter implements ParameterAdapter {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(int.class);
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, String parameterName) throws Exception {
        return Integer.valueOf(request.getParameter(parameterName));
    }
}
