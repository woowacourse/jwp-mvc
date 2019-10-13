package nextstep.mvc.tobe.argumentresolver;

import nextstep.utils.PrimitiveParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrimitiveArgumentResolver implements ArgumentResolver {

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isPrimitive() || methodParameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        Class<?> type = methodParameter.getParameterType();
        String parameterName = methodParameter.getParameterName();
        String value = request.getParameter(parameterName);

        return PrimitiveParser.getPrimitive(type, value);
    }
}
