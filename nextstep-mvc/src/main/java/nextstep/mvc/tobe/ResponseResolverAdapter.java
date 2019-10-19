package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class ResponseResolverAdapter implements ArgumentResolverAdapter {
    private HttpServletResponse response;

    public ResponseResolverAdapter(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public boolean match(Parameter parameter) {
        Class<?> parameterType = parameter.getType();

        return parameterType.equals(HttpServletResponse.class);
    }

    @Override
    public Object get(Parameter parameter) {
        return response;
    }
}
