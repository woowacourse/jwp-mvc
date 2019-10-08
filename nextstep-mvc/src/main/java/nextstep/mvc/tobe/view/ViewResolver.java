package nextstep.mvc.tobe.view;

public class ViewResolver {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    //redirectview, jspview, jsonview

    public View resolveViewName(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName);
        }
        return new JspView(viewName);
    }
}
