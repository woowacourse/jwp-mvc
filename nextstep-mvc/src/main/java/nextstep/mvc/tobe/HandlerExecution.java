package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private final Object instance;
    private final Method method;

    public HandlerExecution(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object view = method.invoke(instance, request, response);
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
            "instance=" + instance +
            ", method=" + method +
            '}';
    }
}
