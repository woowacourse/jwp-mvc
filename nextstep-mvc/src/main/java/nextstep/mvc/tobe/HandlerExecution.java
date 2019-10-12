package nextstep.mvc.tobe;

import nextstep.mvc.tobe.argumentresolver.ArgumentResolver;
import nextstep.mvc.tobe.argumentresolver.HttpSessionArgumentResolver;
import nextstep.mvc.tobe.argumentresolver.MethodParameter;
import nextstep.mvc.tobe.argumentresolver.MethodParameters;
import nextstep.mvc.tobe.argumentresolver.ObjectArgumentResolver;
import nextstep.mvc.tobe.argumentresolver.PrimitiveArgumentResolver;
import nextstep.mvc.tobe.argumentresolver.ServletArgumentResolver;
import nextstep.mvc.tobe.exception.NotSupportedArgumentResolverException;
import nextstep.mvc.tobe.exception.NotSupportedReturnValueException;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

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
        argumentResolvers.add(new HttpSessionArgumentResolver());
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


        return getModelAndView(method.invoke(object, arguments));
    }

    private ModelAndView getModelAndView(Object returnValue) {
        if (returnValue instanceof String) {
            return new ModelAndView((String) returnValue);
        }

        if (returnValue instanceof View) {
            return new ModelAndView((View) returnValue);
        }

        if (returnValue instanceof ModelAndView) {
            return (ModelAndView) returnValue;
        }

        throw new NotSupportedReturnValueException();
    }

    private Object resolve(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        for (ArgumentResolver argumentResolver : argumentResolvers) {
            if (argumentResolver.supports(methodParameter)) {
                return argumentResolver.resolve(methodParameter, request, response);
            }
        }
        throw new NotSupportedArgumentResolverException();
    }
}
