package nextstep.mvc.tobe.argumentResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public class RequestResolverAdapter implements ArgumentResolverAdapter {
    private HttpServletRequest request;

    public RequestResolverAdapter(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean match(Parameter parameter) {
        Class<?> parameterType = parameter.getType();

        return parameterType.equals(HttpServletRequest.class);
    }

    @Override
    public Object get(Parameter parameter) {
        return request;
    }
}
