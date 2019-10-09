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

    private final ManualHandlerMapping manualHandlerMapping;

    public ManualHandlerMappingAdapter(final ManualHandlerMapping manualHandlerMapping) {
        this.manualHandlerMapping = manualHandlerMapping;
    }

    @Override
    public void initialize() {
        manualHandlerMapping.initialize();
    }

    @Override
    public boolean isSupport(HttpServletRequest request) {
        return manualHandlerMapping.containsKey(request);
    }

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String execution = manualHandlerMapping.getHandler(request)
                .execute(request, response);
        return new ModelAndView(processView(execution));
    }

    private View processView(final String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
        }
        return new JspView(viewName);
    }
}
