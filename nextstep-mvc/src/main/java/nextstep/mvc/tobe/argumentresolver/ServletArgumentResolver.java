package nextstep.mvc.tobe.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return ServletArgument.supports(methodParameter.getType());
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getType();
        ServletArgument servletArgument = ServletArgument.getResolver(parameterType);
        return servletArgument.resolve(request, response);
    }
}
