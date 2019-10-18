package nextstep.mvc.tobe.controllermapper.adepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ParameterAdapter {
    boolean supports(MethodParameter methodParameter);

    Object cast(
            HttpServletRequest request,
            HttpServletResponse response,
            MethodParameter methodParameter) throws Exception;
}
