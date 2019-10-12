package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public class JspViewResolver implements ViewResolver {
    private static final String JSP_EXTENSION = ".jsp";

    @Override
    public boolean support(ModelAndView mav) {
        return mav.getViewName().contains(JSP_EXTENSION);
    }

    @Override
    public View resolve(ModelAndView mav) {
        return new JspView(mav.getViewName());
    }
}
