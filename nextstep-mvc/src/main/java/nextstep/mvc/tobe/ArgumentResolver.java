package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ArgumentResolver {
    boolean supports(MethodParameter methodParameter);

    Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response);
}
