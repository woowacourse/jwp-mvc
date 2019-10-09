package nextstep.mvc.tobe.view;

public class StringViewResolver implements ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    public boolean canHandle(ModelAndView modelAndView) {
        return modelAndView.getViewName() != null;
    }

    @Override
    public View resolveView(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName);
        }
        return new JspView(viewName);
    }
}
