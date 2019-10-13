package nextstep.mvc.resolver;

import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public boolean supports(ModelAndView mav) {
        return mav.getViewName().endsWith(JSP_SUFFIX);
    }

    @Override
    public View resolveView(Object view) {
        return new JspView((String) view);
    }
}
