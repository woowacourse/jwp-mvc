package nextstep.mvc.tobe;

import nextstep.web.annotation.RequestBody;
import nextstep.web.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
                    if (parameter.isAnnotationPresent(RequestBody.class)) {
                        return getRequestBody(request, parameter);
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

    //Todo: Constructor가 여러개일 때 어떤걸 해주는게 좋을까?
    private Object getRequestBody(HttpServletRequest request, Parameter parameter) {
        List<Constructor> constructors = Arrays.asList(parameter.getType().getConstructors());

        List<Field> fields = Arrays.asList(parameter.getType().getDeclaredFields());

        List<Object> params = fields.stream()
                .map(field -> request.getParameter(field.getName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Constructor constructor = constructors.stream()
                .filter(con -> con.getParameterCount() == params.size())
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

        try {
            return constructor.newInstance(params.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }
}
