package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerExecution implements Handler {
    private final Method method;
    private final Object object;
    private static List<ArgumentResolver> argumentResolvers = new ArrayList<>();

    static {
        argumentResolvers.add(new ObjectArgumentResolver());
        argumentResolvers.add(new PrimitiveArgumentResolver());
        argumentResolvers.add(new ServletArgumentResolver());
    }


    public HandlerExecution(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MethodParameters methodParameters = MethodParameters.of(method);

        Object[] arguments = new Object[methodParameters.getParameterLength()];
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = resolve(methodParameters.get(i), request, response);
        }

        return (ModelAndView) method.invoke(object, arguments);
    }

    private Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        for (ArgumentResolver argumentResolver : argumentResolvers) {
            if (argumentResolver.supports(methodParameter)) {
                return argumentResolver.resolve(methodParameter, request, response);
            }
        }
        return null;
    }
}
