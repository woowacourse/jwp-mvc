package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final Class<?> clazz;
    private final Method method;

    public HandlerExecution(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object view = method.invoke(clazz.getDeclaredConstructor().newInstance(), request, response);
        if (view instanceof String) {
            if (((String) view).startsWith(DEFAULT_REDIRECT_PREFIX)) {
                return new ModelAndView(new RedirectView(((String) view).substring(DEFAULT_REDIRECT_PREFIX.length())));
            }
            return new ModelAndView(new JspView((String) view));
        }
        return (ModelAndView) view;
    }

    @Override
    public String toString() {
        return "HandlerExecution{" +
            "clazz=" + clazz +
            ", method=" + method +
            '}';
    }
}
