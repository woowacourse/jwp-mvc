package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ArgumentResolver {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ArgumentResolver(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Object[] resolve(Method method) {
        List<Object> result = new LinkedList<>();
        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            Optional.ofNullable(matchParameter(parameter)).ifPresent(result::add);
        }

        return result.toArray();
    }

    private Object matchParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return requestParamValue(parameter);
        }

        if (isHttpServletRequest(parameter)) {
            return request;
        }

        if (isHttpServletResponse(parameter)) {
            return response;
        }
        return null;
    }

    private String requestParamValue(Parameter parameter) {
        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
        String parameterName = annotation.value();
        return request.getParameter(parameterName);
    }

    private boolean isHttpServletRequest(Parameter parameter) {
        Class<?> parameterType = parameter.getType();

        return parameterType.equals(HttpServletRequest.class);
    }

    private boolean isHttpServletResponse(Parameter parameter) {
        Class<?> parameterType = parameter.getType();

        return parameterType.equals(HttpServletResponse.class);
    }
}
