package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private final Method method;
    private final Class<?> clazz;
    private final ViewResolver viewResolver;

    public HandlerExecution(Method method, Class<?> clazz, ViewResolver viewResolver) {
        this.method = method;
        this.clazz = clazz;
        this.viewResolver = viewResolver;
    }

    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), req, resp);
        View view = viewResolver.resolve(result);
        return new ModelAndView(view);
    }
}
