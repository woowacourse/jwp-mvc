package nextstep.mvc.adapter;

import nextstep.mvc.exception.ViewNameResolveException;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

public class ViewNameResolver {
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String JSP_SUFFIX = ".jsp";

    public static View resolve(String viewName) {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(REDIRECT_PREFIX.length()));
        }
        if (viewName.endsWith(JSP_SUFFIX)) {
            return new JspView(viewName);
        }
        throw new ViewNameResolveException("해당하는 View가 없습니다.");
    }
}
