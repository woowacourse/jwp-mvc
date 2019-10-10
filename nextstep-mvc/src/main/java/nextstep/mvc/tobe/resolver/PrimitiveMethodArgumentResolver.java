package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.WebRequest;
import nextstep.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;

public class PrimitiveMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return TypeConverter.contains(methodParameter.getType());
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        final HttpServletRequest request = webRequest.getRequest();
        final String value = methodParameter.getName();
        return TypeConverter.to(methodParameter.getType()).apply(request.getParameter(value));
    }
}
