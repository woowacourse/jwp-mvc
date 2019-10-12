package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LongParameterAdepter implements ParameterAdepter {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(long.class);
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, String parameterName) throws Exception {
        return Long.valueOf(request.getParameter(parameterName));
    }
}
