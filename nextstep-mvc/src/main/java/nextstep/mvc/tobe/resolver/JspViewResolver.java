package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String JSP = ".jsp";

    @Override
    public boolean supports(ModelAndView mav) {
        return mav.getViewName().endsWith(JSP);
    }

    @Override
    public View resolveViewName(String viewName) {
        return new JspView(viewName);
    }
}
