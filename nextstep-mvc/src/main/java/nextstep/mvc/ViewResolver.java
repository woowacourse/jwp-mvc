package nextstep.mvc;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;

public class ViewResolver {
    public ModelAndView resolve(Object view) {
        if (view instanceof ModelAndView) {
            return (ModelAndView) view;
        }
        return new ModelAndView(new JspView((String) view));
    }
}
