package nextstep.mvc.tobe;

import nextstep.mvc.tobe.argumentResolver.ArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private Class<?> clazz;
    private Method method;

    public HandlerExecution(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object[] parameters = getParameters(request, response);

        if (method.getReturnType().equals(String.class)) {
            String viewPath = method.invoke(clazz.newInstance(), parameters).toString();
            return new ModelAndView(viewPath);
        }

        return (ModelAndView) method.invoke(clazz.newInstance(), parameters);
    }

    private Object[] getParameters(HttpServletRequest request, HttpServletResponse response) {
        try {
            ArgumentResolver argumentResolver = new ArgumentResolver(request, response);
            return argumentResolver.resolve(method);
        } catch (NotMatchParameterException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new NotMatchParameterException();
        }
    }
}
