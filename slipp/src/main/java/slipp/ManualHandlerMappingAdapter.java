package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.mvc.tobe.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManualHandlerMappingAdapter implements HandlerMapping {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final ManualHandlerMapping handlerMapping = new ManualHandlerMapping();

    @Override
    public void initialize() {
        handlerMapping.initialize();
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String url = handlerMapping.getHandler(request.getRequestURI()).execute(request, response);
        return new ModelAndView(makeView(url));
    }

    private View makeView(final String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            final String redirectName = viewName.substring(DEFAULT_REDIRECT_PREFIX.length());
            return new RedirectView(redirectName);
        }
        return new JspView(viewName);
    }
}
