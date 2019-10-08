package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerExecution {
    private Object handler;
    private Method method;

    public HandlerExecution(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    //Todo: RequestParam 작업
    //Todo: RequestBody 작업
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Object> arguments = Arrays.stream(method.getParameters())
                .map(parameter -> {
                    if (parameter.getType().equals(HttpServletRequest.class)) {
                        return request;
                    }
                    if (parameter.getType().equals(HttpServletResponse.class)) {
                        return response;
                    }
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        return getRequestParam(request, parameter);
                    }
                    return null;
                })
                .collect(Collectors.toList());
        return method.invoke(handler, arguments.toArray());
    }

    private Object getRequestParam(HttpServletRequest request, Parameter parameter) {
        String value = parameter.getAnnotation(RequestParam.class).value();
        return request.getParameter(value);
    }

    private List<Object> getRequestParam(HttpServletRequest request) {
        List<Object> parameters = Arrays.stream(method.getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(RequestParam.class))
                .map(parameter -> {
                    String value = parameter.getAnnotation(RequestParam.class).value();
                    return request.getParameter(value);
                })
                .collect(Collectors.toList());
        return parameters;
    }
}
