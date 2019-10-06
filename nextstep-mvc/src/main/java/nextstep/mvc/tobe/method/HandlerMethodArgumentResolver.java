package nextstep.mvc.tobe.method;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public interface HandlerMethodArgumentResolver {
    boolean supports(final MethodParameter methodParameter);

    // todo method 없이 하는 방법 or 더 나은 방법 생각
    Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter, final Method method);
}
