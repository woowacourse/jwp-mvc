package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.exception.NoSuchViewException;

public class ViewResolver {
    private static final String EXTENSION_OF_JSP = ".jsp";
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    public static View resolve(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
        }
        if (viewName.contains(EXTENSION_OF_JSP)) {
            return new JspView(viewName);
        }
        throw new NoSuchViewException();
    }
}
