package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public class RequestParamResolverAdapter implements ArgumentResolverAdapter {
    private HttpServletRequest request;

    public RequestParamResolverAdapter(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean match(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class);
    }

    @Override
    public Object get(Parameter parameter) {
        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
        String parameterName = annotation.value();
        return request.getParameter(parameterName);
    }
}
