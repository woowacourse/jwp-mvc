package nextstep.mvc.tobe.viewResolver;

import nextstep.mvc.View;
import nextstep.mvc.ViewResolver;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;

public class JspViewResolver implements ViewResolver {
    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public boolean supports(ModelAndView mv) {
        String viewName = mv.getViewName();
        return viewName.endsWith(JSP_SUFFIX);
    }

    @Override
    public View resolve(ModelAndView mv) {
        return new JspView(mv.getViewName());
    }
}
