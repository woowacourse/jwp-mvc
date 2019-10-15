package nextstep.mvc.tobe.argumentresolver;

import nextstep.web.support.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletArgumentResolver implements HandlerMethodArgumentResolver{
    @Override
    public boolean supports(MethodParameter parameter) {
        return ServletParser.supports(parameter.getParameterType());
    }

    @Override
    public Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return ServletParser.findParser(parameter.getParameterType())
                .parse(request, response);
    }
}
