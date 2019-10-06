package nextstep;

import com.sun.deploy.net.HttpRequest;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ControllerHandlerAdapter implements HandlerAdapter {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean supports(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (supports(handler)) {
            String  viewName = ((Controller) handler).execute(request, response);
            View view = createView(viewName);
            return new ModelAndView(view);
        }
        throw new IllegalArgumentException("지원하지 않는 객체입니다.");
    }

    private View createView(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
        }
        return new JspView(viewName);
    }
}
