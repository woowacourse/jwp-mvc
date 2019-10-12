package nextstep.mvc.tobe.argumentresolver;

import nextstep.web.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class RequestParamResolver implements Argument {
    @Override
    public boolean isMapping(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        return request.getParameter(parameter.getAnnotation(RequestParam.class).value());
    }
}
