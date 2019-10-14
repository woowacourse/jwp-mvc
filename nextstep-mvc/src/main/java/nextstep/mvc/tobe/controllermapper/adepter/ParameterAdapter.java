package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ParameterAdapter {
    boolean supports(Class<?> clazz);

    Object cast(
            HttpServletRequest request,
            HttpServletResponse response,
            String parameterName) throws Exception;
}
