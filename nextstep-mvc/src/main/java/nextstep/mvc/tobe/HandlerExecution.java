package nextstep.mvc.tobe;

import nextstep.mvc.tobe.argumentresolver.Argument;
import nextstep.mvc.tobe.argumentresolver.ArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
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

    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Object> arguments = Arrays.stream(method.getParameters())
                .map(parameter -> {
                    Argument argument = ArgumentResolver.getInstance().resolveParam(parameter);
                    return argument.resolve(request, response, parameter);
                })
                .collect(Collectors.toList());
        return method.invoke(handler, arguments.toArray());
    }
}
