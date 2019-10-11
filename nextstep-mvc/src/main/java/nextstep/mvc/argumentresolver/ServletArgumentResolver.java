package nextstep.mvc.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletArgumentResolver implements ArgumentResolver {
    @Override
    public boolean canResolve(MethodParameter methodParameter) {
        Class<?> type = methodParameter.getType();
        return ServletArgumentConverter.supports(type);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        Class<?> paramType = methodParameter.getType();
        ServletArgumentConverter converter = ServletArgumentConverter.getServletArgumentConverter(paramType);
        return converter.convert(request, response);
    }
}