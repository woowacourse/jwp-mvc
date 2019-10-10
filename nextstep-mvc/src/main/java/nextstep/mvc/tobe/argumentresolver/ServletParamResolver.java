package nextstep.mvc.tobe.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class ServletParamResolver implements Argument {
    @Override
    public boolean isMapping(Parameter parameter) {
        return getType(parameter).equals(HttpServletRequest.class) || getType(parameter).equals(HttpServletResponse.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        if (getType(parameter).equals(HttpServletRequest.class)) {
            return request;
        }
        return response;
    }

    private Class<?> getType(Parameter parameter) {
        return parameter.getType();
    }

}
