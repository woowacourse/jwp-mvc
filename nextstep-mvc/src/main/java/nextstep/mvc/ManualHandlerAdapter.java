package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static nextstep.mvc.tobe.RedirectView.DEFAULT_REDIRECT_PREFIX;

public class ManualHandlerAdapter implements HandlerAdapter {

    private final HandlerMapping manualHandlerMapping;

    public ManualHandlerAdapter(HandlerMapping manualHandlerMapping) {
        manualHandlerMapping.initialize();
        this.manualHandlerMapping = manualHandlerMapping;
    }

    @Override
    public boolean isSupported(HttpServletRequest request) {
        return Objects.nonNull(manualHandlerMapping.getHandler(request));
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Controller handler = (Controller) manualHandlerMapping.getHandler(request);
        String viewName = handler.execute(request, response);

        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new ModelAndView(new RedirectView(viewName));
        }

        return new ModelAndView(new JspView(viewName));
    }
}
