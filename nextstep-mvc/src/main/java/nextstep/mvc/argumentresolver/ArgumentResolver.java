package nextstep.mvc.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ArgumentResolver {
    boolean canResolve(MethodParameter methodParameter);

    Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter);
}